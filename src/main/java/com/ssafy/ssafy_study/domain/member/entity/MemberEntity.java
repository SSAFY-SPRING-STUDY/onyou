package com.ssafy.ssafy_study.domain.member.entity;

import lombok.Getter;

@Getter
public class MemberEntity {

    private Long id;
    private String username;
    private String password;
    private String nickname;

    private static long AUTO_INCREMENT = 1l;

    private MemberEntity(Long id, String username, String password, String nickname){
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public static MemberEntity create(String username, String password, String nickname){
        Long newId = AUTO_INCREMENT++;
        return new MemberEntity(newId, username, password, nickname);
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }




}
