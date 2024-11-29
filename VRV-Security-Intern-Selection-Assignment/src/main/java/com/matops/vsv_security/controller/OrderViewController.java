package com.matops.vsv_security.controller;

import com.matops.vsv_security.model.Order;
import com.matops.vsv_security.model.Product;
import com.matops.vsv_security.repository.OrderRepository;
import com.matops.vsv_security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderViewController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Show create order form with product list
    @GetMapping("/orders/create")
    public String showCreateOrderForm(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "orders/create-order";
    }

    // Handle order creation
    @PostMapping("/orders/create")
    public String createOrder(
            @RequestParam String customerEmail,
            @RequestParam List<Long> productIds) {

        // Fetch selected products
        List<Product> selectedProducts = productRepository.findAllById(productIds);

        if (selectedProducts.isEmpty()) {
            // Handle the case where no products are selected (optional)
            return "redirect:/orders/create?error=No products selected";
        }

        // Create and configure the order
        Order order = new Order();
        order.setCustomerEmail(customerEmail);
        order.setProducts(selectedProducts); // Set the products for the order

        // Save the order with associated products
        orderRepository.save(order);

        return "redirect:/orders";
    }

    // List all orders
    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orders/list-orders";
    }
}
