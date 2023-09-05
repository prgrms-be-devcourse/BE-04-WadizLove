package com.prgrms.wadiz.domain.post.service;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceFacadeTest {
    @InjectMocks
    private PostServiceFacade postServiceFacade;

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
        Long actualPostId = postServiceFacade.createPost(projectServiceDTO, requestDTO);

        // then
        Post actualPost = postRepository.findById(actualPostId).get();

        assertThat(actualPost, samePropertyValuesAs(expectedPost));
    }

    @Test
    @DisplayName("[성공] Post 정보 단건 조회")
    void getPost() {
        // given
        Long projectId = 1L;

        Post post = Post.builder()
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
        PostResponseDTO actualPostResponseDTO = postServiceFacade.getPostByProjectId(projectId);

        // then
        assertThat(actualPostResponseDTO, samePropertyValuesAs(expectedPostResponseDTO));
    }

    @Test
    @DisplayName("[성공] Post 정보 수정")
    void updatePost() {
        // given
        Long projectId = 1L;

        Post befoerPost = Post.builder()
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        Post afterPost = Post.builder()
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
        postServiceFacade.updatePost(projectId, postUpdateRequestDTO);

        // then
        assertThat(postUpdateRequestDTO.postTitle(), is(afterPost.getPostTitle()));
        assertThat(postUpdateRequestDTO.postDescription(), is(afterPost.getPostDescription()));
        assertThat(postUpdateRequestDTO.postThumbNailImage(), is(afterPost.getPostThumbNailImage()));
        assertThat(postUpdateRequestDTO.postContentImage(), is(afterPost.getPostContentImage()));
    }
}