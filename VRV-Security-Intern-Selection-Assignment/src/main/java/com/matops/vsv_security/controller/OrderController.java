package com.matops.vsv_security.controller;

import com.matops.vsv_security.model.Order;
import com.matops.vsv_security.model.Product;
import com.matops.vsv_security.repository.OrderRepository;
import com.matops.vsv_security.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order orderRequest) {
        List<Product> products = productRepository.findAllById(
                orderRequest.getProducts().stream().map(Product::getId).toList());
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        orderRequest.setProducts(products);
        return ResponseEntity.ok(orderRepository.save(orderRequest));
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
