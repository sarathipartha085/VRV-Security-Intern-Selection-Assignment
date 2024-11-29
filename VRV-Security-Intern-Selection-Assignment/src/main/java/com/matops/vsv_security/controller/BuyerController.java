package com.matops.vsv_security.controller;

import com.matops.vsv_security.model.Product;
import com.matops.vsv_security.model.Order;
import com.matops.vsv_security.service.ProductService;
import com.matops.vsv_security.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BuyerController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/buyer/products")
    public String browseProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "buyer/products";
    }

    @PostMapping("/buyer/orders")
    public String purchaseProducts(@RequestParam Long productId) {
        Order order = orderService.createOrder(productId);
        orderService.saveOrder(order);
        return "redirect:/buyer/products";
    }

    @GetMapping("/buyer/orders/history")
    public String viewOrderHistory(Model model) {
        Long buyerId = 1L;
        List<Order> orders = orderService.getOrdersByBuyerId(buyerId);
        model.addAttribute("orders", orders);
        return "buyer/order-history";
    }
}
