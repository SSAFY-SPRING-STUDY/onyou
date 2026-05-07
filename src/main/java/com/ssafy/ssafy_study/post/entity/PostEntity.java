package com.ssafy.ssafy_study.post.entity;

import lombok.Getter;

@Getter
public class PostEntity {
    private static long AUTO_INCREMENT_ID = 1L;

    private final Long id;
    private String title;
    private String content;
    private final String author;

    public PostEntity(String title, String content, String author) {
        this.id = AUTO_INCREMENT_ID++;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
