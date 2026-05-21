package com.ssafy.ssafy_study.domain.auth.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "발급된 access token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,
        @Schema(description = "토큰 타입", example = "Bearer ")
        String tokenType
) {

    public static LoginResponse from(String accessToken){
        return new LoginResponse(accessToken, "Bearer ");
    }
}
