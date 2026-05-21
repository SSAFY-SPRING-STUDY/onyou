package com.ssafy.ssafy_study.domain.post.controller.dto;

import com.ssafy.ssafy_study.domain.member.controller.dto.MemberResponse;
import com.ssafy.ssafy_study.domain.post.entity.PostEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

public record PostResponse(
        @Schema(description = "게시글 고유 ID", example = "1")
        Long id,
        @Schema(description = "제목", example = "안녕하세요, SSAFY입니다.")
        String title,
        @Schema(description = "내용", example = "반갑습니다. 열심히 공부해요!")
        String content,
        @Schema(description = "작성자 정보")
        MemberResponse author
) {
    public static PostResponse from(PostEntity postEntity) {
        return new PostResponse(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                MemberResponse.from(Optional.of(postEntity.getAuthor()))
        );
    }
}
