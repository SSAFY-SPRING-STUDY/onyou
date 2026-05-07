package com.ssafy.ssafy_study.post;

import com.ssafy.ssafy_study.post.controller.dto.PostRequest;
import com.ssafy.ssafy_study.post.controller.dto.PostResponse;
import com.ssafy.ssafy_study.post.entity.PostEntity;
import com.ssafy.ssafy_study.post.repository.PostRepository;
import com.ssafy.ssafy_study.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() throws Exception {
        // Reset AUTO_INCREMENT_ID
        Field field = PostEntity.class.getDeclaredField("AUTO_INCREMENT_ID");
        field.setAccessible(true);
        field.set(null, 1L);

        postRepository = new PostRepository();
        postService = new PostService(postRepository);
    }

    @Test
    void 게시글_저장_성공() {
        PostRequest request = new PostRequest("title", "content", "author");
        PostResponse response = postService.save(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("title");
        assertThat(response.author()).isEqualTo("author");
    }

    @Test
    void 모든_게시글_조회_성공() {
        postService.save(new PostRequest("t1", "c1", "a1"));
        postService.save(new PostRequest("t2", "c2", "a2"));

        List<PostResponse> posts = postService.getAllPosts();

        assertThat(posts).hasSize(2);
    }

    @Test
    void 게시글_ID로_조회_성공() {
        postService.save(new PostRequest("title", "content", "author"));

        PostResponse response = postService.findById(1L);

        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void 게시글_조회_실패_존재하지_않는_ID() {
        assertThatThrownBy(() -> postService.findById(999L))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void 게시글_수정_성공() {
        postService.save(new PostRequest("title", "content", "author"));
        PostRequest updateRequest = new PostRequest("updated", "updated", "author");

        PostResponse response = postService.update(updateRequest, 1L);

        assertThat(response.title()).isEqualTo("updated");
        assertThat(response.content()).isEqualTo("updated");
    }

    @Test
    void 게시글_삭제_성공() {
        postService.save(new PostRequest("title", "content", "author"));
        postService.delete(1L);

        assertThat(postService.getAllPosts()).isEmpty();
    }
}
