package com.codeit.torip.auth.controller;

import com.codeit.torip.auth.dto.EmailCheckResponse;
import com.codeit.torip.auth.dto.LoginRequest;
import com.codeit.torip.auth.dto.RegisterRequest;
import com.codeit.torip.auth.dto.TokenResponse;
import com.codeit.torip.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "이메일과 비밀번호를 받아 토큰을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "로그인 실패")
            }
    )
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        var tokenResponse = authService.login(loginRequest);
        return tokenResponse;
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "유저네임, 이메일, 비밀번호를 받아 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패")
            }
    )
    public TokenResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신 API", description = "리프레시 토큰을 받아 새로운 액세스 토큰을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
                    @ApiResponse(responseCode = "400", description = "토큰 갱신 실패")
            }
    )
    public TokenResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @GetMapping("/register/username/exists")
    @Operation(summary = "이메일 중복 검사 API", description = "이메일을 받아서 중복인지 검사한다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "이메일 중복 검사 성공."),
                    @ApiResponse(responseCode = "400",
                            description = "이메일 검사 실패. 잘못된 이메일 형식이거나 서버 오류로 인해 검사가 실패했습니다.")
            }
    )
    public ResponseEntity<EmailCheckResponse> checkEmailExists(@RequestParam String username) {
        return new ResponseEntity<>(authService.checkEmailExists(username), HttpStatus.OK);
    }
}
