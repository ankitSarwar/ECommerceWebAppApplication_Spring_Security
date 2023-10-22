package com.example.ECommerce.WebApp.service;

import com.example.ECommerce.WebApp.model.Category;
import com.example.ECommerce.WebApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private JwtService jwtService;


    public List<Category> getAllCategory(){
        List<Category> all = categoryRepository.findAll();
        return all;
    }

    public Category addCategory(Category category){
        categoryRepository.save(category);
        return category;
    }


}
