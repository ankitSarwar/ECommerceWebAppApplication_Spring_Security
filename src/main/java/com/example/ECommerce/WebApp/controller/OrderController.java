package com.example.ECommerce.WebApp.controller;

import com.example.ECommerce.WebApp.model.Category;
import com.example.ECommerce.WebApp.model.Order;
import com.example.ECommerce.WebApp.model.UserInfo;
import com.example.ECommerce.WebApp.repository.OrderRepository;
import com.example.ECommerce.WebApp.repository.UserInfoRepository;
import com.example.ECommerce.WebApp.service.CategoryService;
import com.example.ECommerce.WebApp.service.JwtService;
import com.example.ECommerce.WebApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserInfoRepository userInfoRepository;

    @GetMapping("/getAllCategory")
    public List<Category> getAll(){
        List<Category> allCategory = categoryService.getAllCategory();
       return allCategory;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestParam String token, @RequestBody Order order) {
        if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            // Get user details from your database or user service based on the username
            Optional<UserInfo> userDetails = userInfoRepository.findByName(username);
            if (userDetails.isPresent()) {
                UserInfo user = userDetails.get();

                order.setUserInfo(user);

                orderService.placeOrder(order);

                return new ResponseEntity<>("Order placed successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid token.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")  // postman-> Auth->Bearear token-> add user token
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
