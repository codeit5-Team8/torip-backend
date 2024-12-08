package com.codeit.torip.auth.service;

import com.codeit.torip.auth.dto.response.EmailCheckResponse;
import com.codeit.torip.auth.dto.request.LoginRequest;
import com.codeit.torip.auth.dto.request.RegisterRequest;
import com.codeit.torip.auth.dto.response.TokenResponse;
import com.codeit.torip.auth.util.JwtUtil;
import com.codeit.torip.common.exception.AlertException;
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
        User user = userRepository.findUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾지 못했습니다. : " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return TokenResponse.builder()
                .accessToken(jwtUtil.createAccessToken(loginRequest.getEmail()))
                .refreshToken(jwtUtil.createRefreshToken(loginRequest.getEmail()))
                .build();
    }

    public TokenResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new AlertException("이메일이 중복되었습니다.");
        }

        if (userRepository.existsUserByUsername(registerRequest.getUsername())) {
            throw new AlertException("사용자 이름이 중복되었습니다.");
        }

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

    @Transactional(readOnly = true)
    public TokenResponse refresh(String refreshToken) {
        String accessToken = jwtUtil.refreshAccessToken(refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional(readOnly = true)
    public EmailCheckResponse checkEmailExists(String email) {
        return new EmailCheckResponse(userRepository.existsUserByEmail(email));
    }
}
