package com.matops.vsv_security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/")
    public String redirectToDashboardIfAuthenticated(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";  // Redirect authenticated users to the dashboard
        }
        return "login";  // Show login page for unauthenticated users
    }

    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            model.addAttribute("user", oauth2User.getAttributes());  // Pass OAuth2 user data to the view
        }
        return "dashboard";  // Return the dashboard view
    }

    @GetMapping("/login")
    public String loginPage(Authentication authentication, 
                            @RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "expired", required = false) String expired, 
                            @RequestParam(name = "logout", required = false) String logout,
                            Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/dashboard";  // Redirect already logged-in users to dashboard
        }

        // Add error, expired, or logout messages to the model
        if (error != null) {
            model.addAttribute("error", "Authentication failed. Please try again.");
        }
        if (expired != null) {
            model.addAttribute("expired", "Your session has expired. Please log in again.");
        }
        if (logout != null) {
            model.addAttribute("logout", "You have been logged out successfully.");
        }
        return "login";  // Show the login page
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(Model model) {
        model.addAttribute("message", "You do not have permission to access this page.");
        return "access-denied";  // Show access denied page
    }
}
