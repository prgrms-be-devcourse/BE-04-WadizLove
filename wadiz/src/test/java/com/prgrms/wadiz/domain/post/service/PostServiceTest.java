package com.prgrms.wadiz.domain.post.service;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("[성공] Post 정보 등록")
    void createPost() {
        // given
        ProjectServiceDTO projectServiceDTO = ProjectServiceDTO.builder().build();

        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        Long postId = 1L;
        Post expectedPost = Post.builder()
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();
        ReflectionTestUtils.setField(expectedPost, "postId", postId);

        // mocking
        when(postRepository.save(any())).thenReturn(expectedPost);
        when(postRepository.findById(postId)).thenReturn(Optional.of(expectedPost));

        // when
        Long actualPostId = postService.createPost(projectServiceDTO, requestDTO);

        // then
        Post actualPost = postRepository.findById(actualPostId).get();

        assertThat(actualPost, samePropertyValuesAs(expectedPost));
    }

    @Test
    @DisplayName("[성공] Post 정보 단건 조회")
    void getPost() {
        // given
        Long projectId = 1L;
        Project readyProject = Project.builder().build();

        Post post = Post.builder()
                .project(readyProject)
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        PostResponseDTO expectedPostResponseDTO = PostResponseDTO.builder()
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        // mocking
        when(postRepository.findByProjectId(projectId)).thenReturn(Optional.ofNullable(post));

        // when
        PostResponseDTO actualPostResponseDTO = postService.getPostByProjectId(projectId);

        // then
        assertThat(actualPostResponseDTO, samePropertyValuesAs(expectedPostResponseDTO));
    }

    @Test
    @DisplayName("[성공] Post 정보 수정")
    void updatePost() {
        // given
        Long projectId = 1L;
        Project readyProject = Project.builder().build();

        Post befoerPost = Post.builder()
                .project(readyProject)
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        Post afterPost = Post.builder()
                .project(readyProject)
                .postTitle("아주 싼 피자")
                .postDescription("아주 싼 햄버거의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        PostUpdateRequestDTO postUpdateRequestDTO = new PostUpdateRequestDTO(
                "아주 싼 피자",
                "아주 싼 햄버거의 후속작!",
                "xxx post thumbNail image link xxx",
                "xxx post content image link xxx"
        );

        // mocking
        when(postRepository.findByProjectId(projectId)).thenReturn(Optional.of(befoerPost));

        // when
        postService.updatePost(projectId, postUpdateRequestDTO);

        // then
        assertThat(postUpdateRequestDTO.postTitle(), is(afterPost.getPostTitle()));
        assertThat(postUpdateRequestDTO.postDescription(), is(afterPost.getPostDescription()));
        assertThat(postUpdateRequestDTO.postThumbNailImage(), is(afterPost.getPostThumbNailImage()));
        assertThat(postUpdateRequestDTO.postContentImage(), is(afterPost.getPostContentImage()));
    }

    @Test
    @DisplayName("[예외] Project가 개설된 이후 Post를 수정하려고 하면 예외가 발생한다.")
    void updatePostAfterProjectSetUp() {
        // given
        Long projectId = 1L;
        Project setUpProject = Project.builder().build();
        setUpProject.setUpProject();

        Post setUpPost = Post.builder()
                .project(setUpProject)
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        PostUpdateRequestDTO postUpdateRequestDTO = new PostUpdateRequestDTO(
                "아주 싼 피자",
                "아주 싼 햄버거의 후속작!",
                "xxx post thumbNail image link xxx",
                "xxx post content image link xxx"
        );

        // mocking
        when(postRepository.findByProjectId(projectId)).thenReturn(Optional.of(setUpPost));

        // when
        // then
        assertThatThrownBy(() -> postService.updatePost(projectId, postUpdateRequestDTO))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PROJECT_ACCESS_DENY);
    }

    @Test
    @DisplayName("[예외] Project가 개설된 이후 Post를 삭제하려고 하면 예외가 발생한다.")
    void deletePostAfterProjectSetUp() {
        // given
        Long projectId = 1L;
        Project setUpProject = Project.builder().build();
        setUpProject.setUpProject();

        Post setUpPost = Post.builder()
                .project(setUpProject)
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        // mocking
        when(postRepository.findByProjectId(projectId)).thenReturn(Optional.of(setUpPost));

        // when
        // then
        assertThatThrownBy(() -> postService.deletePost(projectId))
                .isInstanceOf(BaseException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PROJECT_ACCESS_DENY);
    }
}