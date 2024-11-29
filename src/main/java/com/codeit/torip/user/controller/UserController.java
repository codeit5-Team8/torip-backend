package com.codeit.torip.user.controller;

import com.codeit.torip.user.dto.UserResponse;
import com.codeit.torip.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 정보 조회 API")
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "유저 정보 조회 API", description = "토큰을 활용해 유저 정보를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "유저 정보 조회 성공."),
                    @ApiResponse(responseCode = "400",
                            description = "유저 정보 조회 실패")
            }
    )
    public UserResponse getUserInfo() {
        return userService.getUserInfo();
    }
}
