package com.codeit.torip.auth.service;

import com.codeit.torip.auth.dto.LoginRequest;
import com.codeit.torip.auth.dto.RegisterRequest;
import com.codeit.torip.auth.dto.TokenResponse;
import com.codeit.torip.auth.util.JwtUtil;
import com.codeit.torip.user.entity.User;
import com.codeit.torip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾지 못했습니다. : " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return TokenResponse.builder()
                .accessToken(jwtUtil.createAccessToken(loginRequest.getEmail()))
                .refreshToken(jwtUtil.createRefreshToken(loginRequest.getEmail()))
                .build();
    }

    public TokenResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .build();
        userRepository.save(user);

        return TokenResponse.builder()
                .accessToken(jwtUtil.createAccessToken(registerRequest.getEmail()))
                .refreshToken(jwtUtil.createRefreshToken(registerRequest.getEmail()))
                .build();
    }

    public TokenResponse refresh(String refreshToken) {
        String accessToken = jwtUtil.refreshAccessToken(refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
