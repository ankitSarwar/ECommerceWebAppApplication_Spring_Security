package com.example.ECommerce.WebApp.service;

import com.example.ECommerce.WebApp.model.UserInfo;
import com.example.ECommerce.WebApp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserInfo createUser(UserInfo userInfo) {
        UserInfo user = userRepository.findFirstByEmail(userInfo.getEmail());
        if(user != null)
        {
            throw new IllegalStateException("E-commerce user already exists!!!!...sign in instead");
        }
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return userInfo;
    }

    public Optional<UserInfo> getUserById(Integer id) {
        Optional<UserInfo> byId = userRepository.findById(id);
        return byId;
    }

    public UserInfo updateUser(Integer id, UserInfo userInfo) {
        Optional<UserInfo> byId = userRepository.findById(id);
        if(byId.isPresent()){
            UserInfo userInfo1 =byId.get();
            userInfo1.setName(userInfo.getName());
            userInfo1.setPhoneNumber(userInfo.getPhoneNumber());
            return userRepository.save(userInfo1);
        }
        throw new IllegalArgumentException("User not found with ID: " + id);
    }


    public Optional<UserInfo> deleteUser(Integer id) {
        Optional<UserInfo> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.deleteById(id);
            return byId;
        }

        return null;
    }

    public List<UserInfo> getAll() {
        List<UserInfo> all = userRepository.findAll();
        return all;
    }


}
