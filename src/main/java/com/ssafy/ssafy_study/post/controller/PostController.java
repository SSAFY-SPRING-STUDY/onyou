package com.ssafy.ssafy_study.post.controller;

import com.ssafy.ssafy_study.post.controller.dto.PostRequest;
import com.ssafy.ssafy_study.post.controller.dto.PostResponse;
import com.ssafy.ssafy_study.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 작성합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@RequestBody PostRequest request) {
        return postService.save(request);
    }

    @Operation(summary = "전체 게시글 조회", description = "등록된 모든 게시글 목록을 가져옵니다.")
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "게시글 상세 조회", description = "ID를 통해 특정 게시글의 정보를 조회합니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(postService.findById(postId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글의 내용을 업데이트합니다.")
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostRequest request) {
        try {
            return ResponseEntity.ok(postService.update(request, postId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "게시글 삭제", description = "ID에 해당하는 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            postService.delete(postId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
