// package com.matops.vsv_security.model;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.*;


// @Entity
// @Table(name = "useroauthinfo")
// public class UserOAuthInfo {

//     @Id
//     private String email;  // Use email as a unique identifier

//     private String name;
//     private String provider;
//     private String providerId;
//     private String role;  // Add a field to store the user's role

//     // Getters and setters

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getProvider() {
//         return provider;
//     }

//     public void setProvider(String provider) {
//         this.provider = provider;
//     }

//     public String getProviderId() {
//         return providerId;
//     }

//     public void setProviderId(String providerId) {
//         this.providerId = providerId;
//     }

//     public String getRole() {
//         return role;
//     }

//     public void setRole(String role) {
//         this.role = role;
//     }
// }


package com.matops.vsv_security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "useroauthinfo")
public class UserOAuthInfo {

    @Id
    private String email;  // Use email as a unique identifier

    private String name;
    private String provider;
    private String providerId;

    // Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
