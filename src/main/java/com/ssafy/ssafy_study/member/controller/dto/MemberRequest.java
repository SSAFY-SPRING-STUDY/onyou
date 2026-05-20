package com.ssafy.ssafy_study.member.controller.dto;

import com.ssafy.ssafy_study.member.entity.MemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public record MemberRequest(
        @Schema(description = "사용자 아이디", example = "ssafy")
        String username,
        @Schema(description = "비밀번호", example = "password1234")
        String password,
        @Schema(description = "닉네임", example = "김싸피")
        String nickname
) {
    public MemberEntity toEntity(){
        return MemberEntity.create(username,password,nickname);
    }
}
