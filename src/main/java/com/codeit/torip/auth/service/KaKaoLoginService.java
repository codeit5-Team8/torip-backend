package com.codeit.torip.auth.service;

import com.codeit.torip.auth.dto.response.LoginResponse;
import com.codeit.torip.auth.util.JwtUtil;
import com.codeit.torip.common.exception.AlertException;
import com.codeit.torip.user.entity.OauthPlatform;
import com.codeit.torip.user.entity.User;
import com.codeit.torip.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KaKaoLoginService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public LoginResponse loginOrRegister(String token) {
        String email = getEmail(token);

        if (isUserRegister(email)) {
            return login(email);
        }
        return register(email);
    }

    public boolean isUserRegister(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public LoginResponse login(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 이메일입니다."));

        if (!user.getOauthPlatform().equals(OauthPlatform.KAKAO)) {
            throw new AlertException("카카오 로그인이 아닙니다.");
        }

        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(jwtUtil.createAccessToken(email))
                .refreshToken(jwtUtil.createRefreshToken(email))
                .expiredAt(jwtUtil.getAccessTokenExpiredAt()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    public LoginResponse register(String email) {
        User user = User.builder()
                .email(email)
                .username(email)
                .password("oauth")
                .oauthPlatform(OauthPlatform.KAKAO)
                .build();

        userRepository.save(user);

        return LoginResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(jwtUtil.createAccessToken(email))
                .refreshToken(jwtUtil.createRefreshToken(email))
                .expiredAt(jwtUtil.getAccessTokenExpiredAt()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }


    private String getEmail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://kapi.kakao.com/v2/user/me", httpEntity,
                String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AlertException("카카오 로그인 중 에러가 발생하였습니다.");
        }

        String responseBody = Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new AlertException("카카오 로그인 중 응답이 없습니다."));
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("kakao_account").get("email").asText();
        } catch (JsonProcessingException e) {
            throw new AlertException("카카오 로그인 중 에러가 발생하였습니다.");
        }
    }
}
