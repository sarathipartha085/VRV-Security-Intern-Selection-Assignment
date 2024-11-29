package com.matops.vsv_security.service;

import com.matops.vsv_security.model.Order;
import com.matops.vsv_security.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Long productId) {
        Order order = new Order();
        order.setProductId(productId);
        order.setBuyerId(1L); 
        return order;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getOrdersByBuyerId(Long buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }
}