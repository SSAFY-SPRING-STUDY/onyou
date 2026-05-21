package com.ssafy.ssafy_study.post;

import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import com.ssafy.ssafy_study.domain.member.repository.MemberRepository;
import com.ssafy.ssafy_study.domain.post.controller.dto.PostRequest;
import com.ssafy.ssafy_study.domain.post.controller.dto.PostResponse;
import com.ssafy.ssafy_study.domain.post.entity.PostEntity;
import com.ssafy.ssafy_study.domain.post.repository.PostRepository;
import com.ssafy.ssafy_study.domain.post.service.PostService;
import com.ssafy.ssafy_study.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostServiceTest {

    private PostService postService;
    private PostRepository postRepository;
    private MemberRepository memberRepository;
    private MemberEntity testMember;

    @BeforeEach
    void setUp() throws Exception {
        // Reset PostEntity AUTO_INCREMENT_ID
        Field postField = PostEntity.class.getDeclaredField("AUTO_INCREMENT_ID");
        postField.setAccessible(true);
        postField.set(null, 1L);

        // Reset MemberEntity AUTO_INCREMENT
        Field memberField = MemberEntity.class.getDeclaredField("AUTO_INCREMENT");
        memberField.setAccessible(true);
        memberField.set(null, 1L);

        postRepository = new PostRepository();
        memberRepository = new MemberRepository();
        postService = new PostService(postRepository, memberRepository);

        testMember = MemberEntity.create("ssafy", "password", "nickname");
        memberRepository.save(testMember);
    }

    @Test
    void 게시글_저장_성공() {
        PostRequest request = new PostRequest("title", "content");
        PostResponse response = postService.save(request, testMember.getId());

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.title()).isEqualTo("title");
        assertThat(response.author().username()).isEqualTo("ssafy");
    }

    @Test
    void 모든_게시글_조회_성공() {
        postService.save(new PostRequest("t1", "c1"), testMember.getId());
        postService.save(new PostRequest("t2", "c2"), testMember.getId());

        List<PostResponse> posts = postService.getAllPosts();

        assertThat(posts).hasSize(2);
    }

    @Test
    void 게시글_ID로_조회_성공() {
        postService.save(new PostRequest("title", "content"), testMember.getId());

        PostResponse response = postService.findById(1L);

        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    void 게시글_조회_실패_존재하지_않는_ID() {
        assertThatThrownBy(() -> postService.findById(999L))
                .isInstanceOf(CustomException.class);
    }

    @Test
    void 게시글_수정_성공() {
        postService.save(new PostRequest("title", "content"), testMember.getId());
        PostRequest updateRequest = new PostRequest("updated", "updated");

        PostResponse response = postService.update(updateRequest, 1L, testMember.getId());

        assertThat(response.title()).isEqualTo("updated");
        assertThat(response.content()).isEqualTo("updated");
    }

    @Test
    void 게시글_삭제_성공() {
        postService.save(new PostRequest("title", "content"), testMember.getId());
        postService.delete(1L, testMember.getId());

        assertThat(postService.getAllPosts()).isEmpty();
    }
}
