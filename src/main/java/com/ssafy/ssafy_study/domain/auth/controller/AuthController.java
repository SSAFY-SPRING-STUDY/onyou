package com.ssafy.ssafy_study.domain.auth.controller;

import com.ssafy.ssafy_study.domain.auth.controller.dto.LoginRequest;
import com.ssafy.ssafy_study.domain.auth.controller.dto.LoginResponse;
import com.ssafy.ssafy_study.domain.auth.service.AuthService;
import com.ssafy.ssafy_study.domain.auth.util.AuthTokenUtils;
import com.ssafy.ssafy_study.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "로그인", description = "아이디/비밀번호로 로그인하고 access token을 발급합니다.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = authService.login(request);
        return ApiResponse.success("로그인 성공", response);
    }

    @Operation(summary = "로그아웃", description = "Bearer 토큰으로 세션을 삭제합니다.")
    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> logout(
            @Parameter(description = "Bearer access token", required = true, example = "Bearer eyJhbGciOiJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearerToken
    ){
        authService.logout(AuthTokenUtils.parseBearerToken(bearerToken));
        return ApiResponse.success("로그아웃 성공");
    }

}
