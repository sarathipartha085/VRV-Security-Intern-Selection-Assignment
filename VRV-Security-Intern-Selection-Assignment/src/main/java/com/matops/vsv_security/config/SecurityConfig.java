package com.matops.vsv_security.config;

import com.matops.vsv_security.model.UserOAuthInfo;
import com.matops.vsv_security.repository.UserOAuthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserOAuthInfoRepository userOAuthInfoRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/access-denied", "/h2-console/**").permitAll()  // Public endpoints
                .requestMatchers("/seller/**").hasRole("SELLER") // Seller-only pages
                .requestMatchers("/admin/**").hasRole("ADMIN")   // Admin-only pages
                .requestMatchers("/buyer/**").hasRole("BUYER")   // Buyer-only pages
                .requestMatchers("/**").hasRole("ADMIN")  // Admin can access all pages, including seller and buyer
                .anyRequest().authenticated()  // Restrict other requests to authenticated users
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")  // Custom login page
                .failureUrl("/login?error=true")  // Redirect to login with error message
                .defaultSuccessUrl("/dashboard", true)  // Redirect to dashboard on successful login
                .successHandler(this::onAuthenticationSuccess)  // Handle saving user data and assigning roles
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessHandler(logoutSuccessHandler())
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> 
                    response.sendRedirect("/login")
                )
                .accessDeniedPage("/access-denied") 
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login?expired=true")
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions().sameOrigin()
            );

        return http.build();
    }

    @Bean
    public SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler logoutHandler = new SimpleUrlLogoutSuccessHandler();
        logoutHandler.setDefaultTargetUrl("/login?logout=true");
        return logoutHandler;
    }

    // Custom OAuth2 success handler
    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            OAuth2User oAuth2User = oauth2Token.getPrincipal();

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String provider = oauth2Token.getAuthorizedClientRegistrationId(); // Get provider
            String providerId = oAuth2User.getName();

            // Determine the role based on the email
            String role = "ROLE_BUYER"; // Default role is "BUYER"
            if ("21z130@psgitech.ac.in".equals(email)) {
                role = "ROLE_ADMIN"; // Assign admin role to this specific user
            } else if (email != null && email.endsWith("@example.com")) {
                role = "ROLE_SELLER"; // Assign seller role based on email domain
            }

            // Save OAuth2 data in the database
            UserOAuthInfo userOAuthInfo = new UserOAuthInfo();
            userOAuthInfo.setEmail(email);
            userOAuthInfo.setName(name);
            userOAuthInfo.setProvider(provider);
            userOAuthInfo.setProviderId(providerId);

            userOAuthInfoRepository.save(userOAuthInfo); // Persist user info in DB

            // Rebuild Authentication object with updated authorities
            OAuth2AuthenticationToken updatedAuthToken = new OAuth2AuthenticationToken(
                    oAuth2User, 
                    oAuth2User.getAuthorities(),  // Add the new authorities (including the role)
                    oauth2Token.getAuthorizedClientRegistrationId()
            );

            // Set the updated authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(updatedAuthToken);
        }

        // Redirect to the dashboard after login
        response.sendRedirect("/dashboard");
    }

    }
