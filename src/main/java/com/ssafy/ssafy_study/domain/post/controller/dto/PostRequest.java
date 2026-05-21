package com.ssafy.ssafy_study.domain.post.controller.dto;

import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import com.ssafy.ssafy_study.domain.post.entity.PostEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public class PostRequest {
    @Schema(description = "게시글 제목", example = "안녕하세요, SSAFY입니다.")
    private final String title;
    @Schema(description = "게시글 내용", example = "반갑습니다. 열심히 공부해요!")
    private final String content;

    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public PostEntity toEntity(MemberEntity author) {
        return PostEntity.create(title, content, author);
    }


}
