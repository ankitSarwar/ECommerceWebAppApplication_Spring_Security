package com.example.ECommerce.WebApp.service;

import com.example.ECommerce.WebApp.model.Product;
import com.example.ECommerce.WebApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProduct() {
          return productRepository.findAll();
    }
}
