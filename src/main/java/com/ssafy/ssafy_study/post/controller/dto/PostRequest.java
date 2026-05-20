package com.ssafy.ssafy_study.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PostRequest {
    @Schema(description = "게시글 제목", example = "안녕하세요, SSAFY입니다.")
    private final String title;
    @Schema(description = "게시글 내용", example = "반갑습니다. 열심히 공부해요!")
    private final String content;
    @Schema(description = "작성자", example = "김싸피")
    private final String author;

    public PostRequest(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
