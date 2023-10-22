package com.example.ECommerce.WebApp.controller;

import com.example.ECommerce.WebApp.dto.AuthRequest;
import com.example.ECommerce.WebApp.model.Address;
import com.example.ECommerce.WebApp.model.Product;
import com.example.ECommerce.WebApp.model.UserInfo;
import com.example.ECommerce.WebApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    ProductService productService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/new")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        UserInfo createdUserInfo = userService.createUser(userInfo);
        return new ResponseEntity<>(createdUserInfo, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestBody AuthRequest authRequest,@PathVariable Integer id) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
               userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    // address
    @PostMapping("/address")
    public ResponseEntity<String> addresSave(@RequestParam String token, @RequestBody Address address) {
        String saveMessage = addressService.saveAddress(token, address);
        return new ResponseEntity<>(saveMessage, HttpStatus.CREATED);
    }



}