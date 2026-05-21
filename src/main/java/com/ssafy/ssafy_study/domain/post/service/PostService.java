package com.ssafy.ssafy_study.domain.post.service;

import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import com.ssafy.ssafy_study.domain.member.repository.MemberRepository;
import com.ssafy.ssafy_study.domain.post.controller.dto.PostRequest;
import com.ssafy.ssafy_study.domain.post.controller.dto.PostResponse;
import com.ssafy.ssafy_study.domain.post.entity.PostEntity;
import com.ssafy.ssafy_study.domain.post.repository.PostRepository;
import com.ssafy.ssafy_study.global.exception.CustomException;
import com.ssafy.ssafy_study.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse save(PostRequest request, Long authorId) {
        MemberEntity author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        PostEntity postEntity = request.toEntity(author);
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
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return PostResponse.from(postEntity);
    }

    public PostResponse update(PostRequest request, Long id, Long authorId) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!postEntity.getAuthor().getId().equals(authorId)) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }

        postEntity.update(request.getTitle(), request.getContent());
        return PostResponse.from(postEntity);
    }

    public void delete(Long id, Long authorId) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!postEntity.getAuthor().getId().equals(authorId)) {
            throw new CustomException(ErrorCode.INVALID_PERMISSION);
        }

        postRepository.deleteById(id);
    }
}
