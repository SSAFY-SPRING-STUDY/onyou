package com.ssafy.ssafy_study.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
        @Schema(description = "로그인 아이디", example = "ssafy")
        String username,
        @Schema(description = "비밀번호", example = "password1234")
        String password
) {
}
