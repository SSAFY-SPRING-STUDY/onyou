package com.ssafy.ssafy_study.domain.post.entity;

import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import lombok.Getter;

@Getter
public class PostEntity {
    private static long AUTO_INCREMENT_ID = 1L;

    private final Long id;
    private String title;
    private String content;
    private final MemberEntity author;

    public PostEntity(String title, String content, MemberEntity author) {
        this.id = AUTO_INCREMENT_ID++;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static PostEntity create(String title, String content, MemberEntity author) {
        return new PostEntity(title, content, author);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
