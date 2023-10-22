package com.example.ECommerce.WebApp.service;

import com.example.ECommerce.WebApp.model.Order;
import com.example.ECommerce.WebApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order updateOrder(Long id, Order order) {
        Order order1=orderRepository.findById(id);
        if(order1!=null){
            order1.setAddress(order.getAddress());
            order1.setProduct(order.getProduct());
            order1.setProductQuantity(order.getProductQuantity());
            orderRepository.save(order1);
            return order1;
        }else{
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
    }

    public void placeOrder(Order order) {
         orderRepository.save(order);
    }
}
