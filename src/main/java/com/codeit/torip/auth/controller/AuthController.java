package com.codeit.torip.auth.controller;

import com.codeit.torip.auth.dto.response.EmailCheckResponse;
import com.codeit.torip.auth.dto.request.LoginRequest;
import com.codeit.torip.auth.dto.request.RegisterRequest;
import com.codeit.torip.auth.dto.response.TokenResponse;
import com.codeit.torip.auth.service.AuthService;
import com.codeit.torip.common.dto.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1/torip/auth")
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
    public CommonResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return new CommonResponse<TokenResponse>().success(authService.login(loginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "유저네임, 이메일, 비밀번호를 받아 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패")
            }
    )
    public CommonResponse<TokenResponse> register(@RequestBody RegisterRequest registerRequest) {
        return new CommonResponse<TokenResponse>().success(authService.register(registerRequest));
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신 API", description = "리프레시 토큰을 받아 새로운 액세스 토큰을 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
                    @ApiResponse(responseCode = "400", description = "토큰 갱신 실패")
            }
    )
    public CommonResponse<TokenResponse> refresh(@RequestBody String refreshToken) {
        return new CommonResponse<TokenResponse>().success(authService.refresh(refreshToken));
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
    public CommonResponse<EmailCheckResponse> checkEmailExists(@RequestParam String email) {
        return new CommonResponse<EmailCheckResponse>().success(authService.checkEmailExists(email));
    }
}
