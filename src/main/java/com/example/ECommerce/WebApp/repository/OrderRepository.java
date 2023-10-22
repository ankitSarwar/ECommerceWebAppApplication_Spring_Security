package com.example.ECommerce.WebApp.repository;

import com.example.ECommerce.WebApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findById(Long id);
}
