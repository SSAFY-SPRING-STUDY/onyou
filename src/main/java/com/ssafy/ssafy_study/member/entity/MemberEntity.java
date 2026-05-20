package com.ssafy.ssafy_study.member.entity;

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

    public static boolean checkPassword(String password){
        if(password.equals(password)){
            return true;
        }
        else return false;
    }




}
