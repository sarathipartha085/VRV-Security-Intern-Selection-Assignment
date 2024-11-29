package com.matops.vsv_security.controller;

import com.matops.vsv_security.model.Product;
import com.matops.vsv_security.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SellerController {

    @Autowired
    private ProductService productService;

    @GetMapping("/seller/products")
    public String getAllProducts() {
        // Logic to fetch and display products
        return "seller/products"; // returns the Thymeleaf template for seller
    }

    @PostMapping("/seller/products")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/seller/products";
    }

    @PutMapping("/seller/products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        productService.updateProduct(id, product);
        return "redirect:/seller/products";
    }

    @DeleteMapping("/seller/products/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/seller/products";
    }
}
