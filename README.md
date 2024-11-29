# VRV Security - Intern Selection Assignment - Role-Based Authentication and Seller Dashboard

## Overview
Matops Shop is a robust Spring Boot application designed to implement role-based authentication and authorization using OAuth2. It provides a flexible platform for managing different user roles (Admin, Seller, Buyer) and offers a tailored dashboard experience for sellers to manage their products, view orders, and analyze product-specific statistics.

This repository demonstrates the integration of secure authentication mechanisms and modular role-specific functionality in a Spring Boot application.

---

## Project Idea
The core idea behind this project is to develop a role-based application catering to multiple types of users:
1. **Admin:** High-level access to oversee and manage all platform functionalities.
2. **Seller:** Moderate access for product management, order tracking, and performance analytics.
3. **Buyer:** Limited access for browsing and purchasing products.

This system ensures a seamless and secure experience for different roles while allowing sellers to optimize their operations through insightful statistics and easy-to-use interfaces.

---

## Features

### Authentication and Authorization
- OAuth2-based login with **Google** and **GitHub** OAuth integration.
- Role-based access control for Admin, Seller, and Buyer.
- Custom login and logout handling.
- Session management with maximum session control.

### Seller Dashboard
- **Product Management:**
  - Add, update, and delete products.
  - View a detailed list of products.
- **Order Management:**
  - View order history, including buyer details and purchased products.
- **Statistics:**
  - Analyze product performance (sales and views).
  - Focused on seller-specific metrics.

### Admin and Buyer Roles
- **Admin:**
  - Manage platform-wide users and functionalities.
  - Oversee product listings and order details.
- **Buyer:**
  - Browse and purchase products.
  - Manage their purchase history and profile.

---

## Technologies Used
- **Backend:** Spring Boot (Java 21)
- **Frontend:** Thymeleaf for templating
- **Database:** H2 (for development)
- **Security:** Spring Security with OAuth2
- **Build Tool:** Gradle 8.11+

---

## Setup Instructions

### Prerequisites
1. **Java 21 or higher.**
2. **Gradle 8.11+.**
3. **An IDE like IntelliJ IDEA or Visual Studio Code.**
4. **Google OAuth credentials** for authentication.
5. **GitHub OAuth credentials** for authentication.

---

### Steps to Run the Application

1. **Clone the Repository**
   ```bash
   git clone https://github.com/sarathipartha085/VRV-Security-Intern-Selection-Assignment.git
   cd VRV-Security-Intern-Selection-Assignment
   ```

2. **Configure OAuth2 Authentication**
   - Set up **Google OAuth** and **GitHub OAuth** credentials.
   - Update the `application.properties` with your credentials:
     ```properties
     # GitHub OAuth2 Configuration
     spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
     spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET
     spring.security.oauth2.client.registration.github.redirect-uri={baseUrl}/login/oauth2/code/github
     spring.security.oauth2.client.registration.github.scope=read:user,user:email
     spring.security.oauth2.client.provider.github.authorization-uri=https://github.com/login/oauth/authorize
     spring.security.oauth2.client.provider.github.token-uri=https://github.com/login/oauth/access_token
     spring.security.oauth2.client.provider.github.user-info-uri=https://api.github.com/user
     spring.security.oauth2.client.provider.github.user-name-attribute=id

     # Google OAuth2 Configuration
     spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
     spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
     spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/google
     spring.security.oauth2.client.registration.google.scope=openid,profile,email
     spring.security.oauth2.client.provider.google.authorization-uri=https://accounts.google.com/o/oauth2/auth
     spring.security.oauth2.client.provider.google.token-uri=https://oauth2.googleapis.com/token
     spring.security.oauth2.client.provider.google.user-info-uri=https://www.googleapis.com/oauth2/v3/userinfo
     spring.security.oauth2.client.provider.google.user-name-attribute=sub
     ```

3. **Run the Application**
   You can run the application using Gradle:
   ```bash
   ./gradlew bootRun
   ```

4. **Access the Application**
   Once the application starts, you can access it through:
   - **Login page**: `/login`
   - **Admin Dashboard** (Admin access): `/admin`
   - **Seller Dashboard** (Seller access): `/seller`
   - **Buyer Dashboard** (Buyer access): `/buyer`

---

## API Endpoints

### Authentication
- **Login:** `/login`
- **Logout:** `/logout`

### Seller Endpoints
- **Product Management:**
  - Get all products: `/seller/products` (GET)
  - Add a new product: `/seller/products` (POST)
  - Update a product: `/seller/products/{id}` (PUT)
  - Delete a product: `/seller/products/{id}` (DELETE)
- **Order Management:**
  - View all orders: `/seller/orders` (GET)
- **Statistics:**
  - View product statistics: `/seller/statistics` (GET)

### Admin Endpoints
- **Manage Users:** `/admin/users` (GET, POST, PUT, DELETE)
- **Manage Products:** `/admin/products` (GET, DELETE)
- **View All Orders:** `/admin/orders` (GET)

### Buyer Endpoints
- **Browse Products:** `/buyer/products` (GET)
- **Purchase Products:** `/buyer/orders` (POST)
- **View Purchase History:** `/buyer/orders/history` (GET)

---

## Project Structure
```bash
src/main/java/com/matops/vsv_security
├── config/              # Spring Security configurations
├── controller/          # REST controllers for different roles
│   ├── AdminController  # Controller for admin-specific operations
│   ├── BuyerController  # Controller for buyer-specific operations
│   └── SellerController # Controller for seller-specific operations
├── model/               # Data models (e.g., Seller, Product, Order)
├── repository/          # JPA repositories for database interaction
├── service/             # Business logic and services
│   ├── ProductService   # Service layer for product-related logic
│   ├── SellerService    # Service layer for seller-related logic
│   └── StatisticsService# Service for analytics and statistics
└── resources/
    ├── templates/       # Thymeleaf templates for UI
    │   ├── seller/      # Seller-specific pages (e.g., orders, products)
    │   └── shared/      # Common pages (e.g., login)
    └── application.properties # Configuration properties
```

