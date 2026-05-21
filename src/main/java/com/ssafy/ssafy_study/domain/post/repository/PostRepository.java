package com.ssafy.ssafy_study.domain.post.repository;

import com.ssafy.ssafy_study.domain.post.entity.PostEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepository {
    private final List<PostEntity> posts = new ArrayList<>();

    public PostEntity save(PostEntity postEntity) {
        posts.add(postEntity);
        return postEntity;
    }

    public Optional<PostEntity> findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    public List<PostEntity> findAll() {
        return new ArrayList<>(posts);
    }

    public void deleteById(Long id) {
        posts.removeIf(post -> post.getId().equals(id));
    }
}
