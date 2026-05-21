package com.ssafy.ssafy_study.domain.post.controller;

import com.ssafy.ssafy_study.domain.auth.component.SessionManager;
import com.ssafy.ssafy_study.domain.auth.util.AuthTokenUtils;
import com.ssafy.ssafy_study.domain.post.controller.dto.PostRequest;
import com.ssafy.ssafy_study.domain.post.controller.dto.PostResponse;
import com.ssafy.ssafy_study.domain.post.service.PostService;
import com.ssafy.ssafy_study.global.exception.CustomException;
import com.ssafy.ssafy_study.global.exception.error.ErrorCode;
import com.ssafy.ssafy_study.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final SessionManager sessionManager;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponse> createPost(
            @RequestHeader("Authorization") String token,
            @RequestBody PostRequest request
    ) {
        Long authorId = extractMemberId(token);
        PostResponse response = postService.save(request, authorId);
        return ApiResponse.success("게시글이 생성되었습니다.", response);
    }

    @Operation(summary = "전체 게시글 조회", description = "등록된 모든 게시글 목록을 가져옵니다.")
    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ApiResponse.success("전체 게시글 조회 성공", posts);
    }

    @Operation(summary = "게시글 상세 조회", description = "ID를 통해 특정 게시글의 정보를 조회합니다.")
    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPostById(@PathVariable Long postId) {
        PostResponse post = postService.findById(postId);
        return ApiResponse.success("게시글 상세 조회 성공", post);
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글의 내용을 업데이트합니다.")
    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @RequestHeader("Authorization") String token,
            @PathVariable Long postId,
            @RequestBody PostRequest request
    ) {
        Long authorId = extractMemberId(token);
        PostResponse response = postService.update(request, postId, authorId);
        return ApiResponse.success("게시글이 수정되었습니다.", response);
    }

    @Operation(summary = "게시글 삭제", description = "ID에 해당하는 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePost(
            @RequestHeader("Authorization") String token,
            @PathVariable Long postId
    ) {
        Long authorId = extractMemberId(token);
        postService.delete(postId, authorId);
        return ApiResponse.success("게시글이 삭제되었습니다.");
    }

    private Long extractMemberId(String bearerToken) {
        if (!AuthTokenUtils.isValidBearerToken(bearerToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        String sessionKey = AuthTokenUtils.parseBearerToken(bearerToken);
        return sessionManager.getMemberId(sessionKey);
    }
}
