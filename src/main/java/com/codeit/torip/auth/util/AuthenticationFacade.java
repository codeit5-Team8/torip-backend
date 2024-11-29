package com.codeit.torip.auth.util;

import com.codeit.torip.auth.entity.CustomUserDetail;
import com.codeit.torip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getUserInfo() {
        try {
            CustomUserDetail userDetails = (CustomUserDetail) getAuthentication().getPrincipal();
            return userDetails.getUser();
        } catch (Exception e) {
            throw new AuthenticationServiceException("로그인이 필요합니다.");
        }
    }
}
