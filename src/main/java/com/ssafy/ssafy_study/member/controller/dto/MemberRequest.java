package com.ssafy.ssafy_study.member.controller.dto;

import com.ssafy.ssafy_study.member.entity.MemberEntity;

public record MemberRequest(String username, String password, String nickname) {
    public MemberEntity toEntity(){
        return MemberEntity.create(username,password,nickname);
    }




}
