package com.codeit.torip.auth.util;

import com.codeit.torip.auth.entity.CustomUserDetail;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static User getUserInfo() {
        try {
            CustomUserDetail userDetails = (CustomUserDetail) getAuthentication().getPrincipal();
            return userDetails.getUser();
        } catch (Exception e) {
            throw new AlertException("로그인이 필요합니다.");
        }
    }
    
    public static String getEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return StringUtils.isEmpty(email) ? "" : email;
    }

}
