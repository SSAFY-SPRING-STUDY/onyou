package com.ssafy.ssafy_study.member.controller.dto;

import com.ssafy.ssafy_study.member.entity.MemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public record MemberResponse(
        @Schema(description = "회원 고유 ID", example = "1")
        Long id,
        @Schema(description = "사용자 아이디", example = "ssafy")
        String username,
        @Schema(description = "닉네임", example = "김싸피")
        String nickname
) {
    public static MemberResponse from(Optional<MemberEntity> memberEntity){
        MemberEntity member = memberEntity
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."));
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname()
        );
    }
}