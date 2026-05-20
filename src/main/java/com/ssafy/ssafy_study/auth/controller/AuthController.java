package com.ssafy.ssafy_study.auth.controller;

import com.ssafy.ssafy_study.auth.controller.dto.LoginRequest;
import com.ssafy.ssafy_study.auth.controller.dto.LoginResponse;
import com.ssafy.ssafy_study.auth.service.AuthService;
import com.ssafy.ssafy_study.auth.util.AuthTokenUtils;
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
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @Operation(summary = "로그아웃", description = "Bearer 토큰으로 세션을 삭제합니다.")
    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(
            @Parameter(description = "Bearer access token", required = true, example = "Bearer eyJhbGciOiJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearerToken
    ){
        authService.logout(AuthTokenUtils.parseBearerToken(bearerToken));
    }

}
