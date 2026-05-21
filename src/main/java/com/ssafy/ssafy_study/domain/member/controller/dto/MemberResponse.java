package com.ssafy.ssafy_study.domain.member.controller.dto;

import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import com.ssafy.ssafy_study.global.exception.CustomException;
import com.ssafy.ssafy_study.global.exception.error.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

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
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return new MemberResponse(
                member.getId(),
                member.getUsername(),
                member.getNickname()
        );
    }
}