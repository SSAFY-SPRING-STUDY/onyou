package com.ssafy.ssafy_study.member.controller.dto;

import com.ssafy.ssafy_study.member.entity.MemberEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public record MemberResponse(
        Long id,
        String username,
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