package com.example.ECommerce.WebApp.controller;

import com.example.ECommerce.WebApp.dto.AuthRequest;
import com.example.ECommerce.WebApp.model.Category;
import com.example.ECommerce.WebApp.model.Product;
import com.example.ECommerce.WebApp.model.UserInfo;
import com.example.ECommerce.WebApp.repository.UserInfoRepository;
import com.example.ECommerce.WebApp.service.CategoryService;
import com.example.ECommerce.WebApp.service.JwtService;
import com.example.ECommerce.WebApp.service.ProductService;
import com.example.ECommerce.WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserInfoRepository userInfoRepository;


    // getAll products
    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllProducts(){
        List<Product> product = productService.getAllProduct();
        if(product!=null) {
            return product;
        }else{
            return null;
        }
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
        Optional<UserInfo> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //    user getAll
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserInfo> getAllUser(){
        List<UserInfo> all = userService.getAll();
        return all;
    }


    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestParam String token,@RequestBody Category category){
        if(jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            // Get user details from your database or user service based on the username
            Optional<UserInfo> userDetails = userInfoRepository.findByName(username);
            if (userDetails.isPresent()) {
                UserInfo user = userDetails.get();
                if ("ROLE_ADMIN".equals(user.getRoles())) {
                    Category category1 = categoryService.addCategory(category);
                    return new ResponseEntity<>("User is an admin. category is added", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("User is not an admin.", HttpStatus.UNAUTHORIZED);
                }
            }
            else {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("token is not valid",HttpStatus.NOT_FOUND);
    }


    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestParam String token, @RequestBody Product product) {
        if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            // Get user details from your database or user service based on the username
            Optional<UserInfo> userDetails = userInfoRepository.findByName(username);
            if (userDetails.isPresent()) {
                UserInfo user = userDetails.get();
                if ("ROLE_ADMIN".equals(user.getRoles())) {
                    Product pro = productService.addProduct(product);
                    return new ResponseEntity<>("User is an admin. product is added", HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>("User is not an admin.", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("token is not valid",HttpStatus.NOT_FOUND);
    }


    // user delete
    @DeleteMapping("/delete/User")
    public ResponseEntity<String> deleteUser(@RequestParam String token,@PathVariable Integer id){
        if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            // Get user details from your database or user service based on the username
            Optional<UserInfo> userDetails = userInfoRepository.findByName(username);
            if (userDetails.isPresent()) {
                UserInfo user = userDetails.get();
                if ("ROLE_ADMIN".equals(user.getRoles())) {
                       Optional<UserInfo> user1 = userService.deleteUser(id);
                       if(user1!=null) {
                           return new ResponseEntity<>("User is an admin. user is deleted", HttpStatus.OK);
                       }
                }
                else {
                    return new ResponseEntity<>("User is not an admin.", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("token is not valid",HttpStatus.NOT_FOUND);
    }




}

