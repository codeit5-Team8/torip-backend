package com.codeit.torip.user.service;

import com.codeit.torip.auth.util.AuthUtil;
import com.codeit.torip.user.dto.UserResponse;
import com.codeit.torip.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    public UserResponse getUserInfo() {
        User userInfo = AuthUtil.getUserInfo();
        return userInfo.toResponse();
    }

}
