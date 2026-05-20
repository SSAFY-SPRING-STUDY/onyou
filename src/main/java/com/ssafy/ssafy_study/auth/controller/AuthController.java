package com.ssafy.ssafy_study.auth.controller;

import com.ssafy.ssafy_study.auth.controller.dto.LoginRequest;
import com.ssafy.ssafy_study.auth.controller.dto.LoginResponse;
import com.ssafy.ssafy_study.auth.service.AuthService;
import com.ssafy.ssafy_study.auth.util.AuthTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader("Authorization") String bearerToken){
        authService.logout(AuthTokenUtils.parseBearerToken(bearerToken));
    }

}
