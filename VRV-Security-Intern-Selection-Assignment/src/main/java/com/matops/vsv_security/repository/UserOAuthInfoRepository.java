package com.matops.vsv_security.repository;

import com.matops.vsv_security.model.UserOAuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOAuthInfoRepository extends JpaRepository<UserOAuthInfo, String> {
    UserOAuthInfo findByEmail(String email);  // To retrieve user by email
}
