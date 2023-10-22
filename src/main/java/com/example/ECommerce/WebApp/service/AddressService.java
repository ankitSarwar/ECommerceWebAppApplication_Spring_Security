package com.example.ECommerce.WebApp.service;

import com.example.ECommerce.WebApp.model.Address;
import com.example.ECommerce.WebApp.repository.AddressRepository;
import com.example.ECommerce.WebApp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    private UserInfoRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveAddress(String token, Address address) {
        if (jwtService.validateToken(token)) {
            String user = jwtService.extractUsername(token);
            if (address.getUserInfo() != null && user.equals(address.getUserInfo().getName()) ) {
                address.getUserInfo().setPassword(passwordEncoder.encode(address.getUserInfo().getPassword()));
                addressRepository.save(address);
                return "Address saved successfully";
            }else{
                return "Address not matches with user details";
            }
        }
        else {
            return "Invalid or expired token. Address not saved.";
        }
    }



}
