package com.prgrms.wadiz.domain.post.service;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        Post expectedPost = Post.builder()
                .postTitle("아주 싼 햄버거")
                .postDescription("아주 싼 우산의 후속작!")
                .postThumbNailImage("xxx post thumbNail image link xxx")
                .postContentImage("xxx post content image link xxx")
                .build();

        // mocking
        when(postRepository.save(any())).thenReturn(expectedPost);

        // when
        Post actualPost = postServiceFacade.createPost(requestDTO);

        // then
        assertThat(actualPost, samePropertyValuesAs(expectedPost));
    }

    @Test
    @DisplayName("[성공] Post 정보 단건 조회")
    void getPost() {
        // given
        Long postId = 1L;

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
        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));

        // when
        PostResponseDTO actualPostResponseDTO = postServiceFacade.getPost(postId);

        // then
        assertThat(actualPostResponseDTO, samePropertyValuesAs(expectedPostResponseDTO));
    }
}