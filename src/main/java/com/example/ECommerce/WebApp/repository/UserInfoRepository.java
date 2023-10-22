package com.example.ECommerce.WebApp.repository;

import com.example.ECommerce.WebApp.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByName(String username);

    UserInfo findFirstByEmail(String email);
}
