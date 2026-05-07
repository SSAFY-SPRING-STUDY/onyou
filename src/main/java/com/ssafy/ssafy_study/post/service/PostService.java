package com.ssafy.ssafy_study.post.service;

import com.ssafy.ssafy_study.post.controller.dto.PostRequest;
import com.ssafy.ssafy_study.post.controller.dto.PostResponse;
import com.ssafy.ssafy_study.post.entity.PostEntity;
import com.ssafy.ssafy_study.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponse save(PostRequest request) {
        PostEntity postEntity = new PostEntity(
                request.getTitle(),
                request.getContent(),
                request.getAuthor()
        );
        PostEntity savedEntity = postRepository.save(postEntity);
        return PostResponse.from(savedEntity);
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse findById(Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id: " + id));
        return PostResponse.from(postEntity);
    }

    public PostResponse update(PostRequest request, Long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id: " + id));
        postEntity.update(request.getTitle(), request.getContent());
        return PostResponse.from(postEntity);
    }

    public void delete(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id: " + id));
        postRepository.deleteById(id);
    }
}
