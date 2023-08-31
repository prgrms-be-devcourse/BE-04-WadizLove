package com.prgrms.wadiz.domain.post.service;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostCreateRequestDTO postCreateRequestDTO) {
        Post post = Post.builder()
                .postTitle(postCreateRequestDTO.postTitle())
                .postDescription(postCreateRequestDTO.PostDescription())
                .postThumbNailImage(postCreateRequestDTO.postThumbNail())
                .postContentImage(postCreateRequestDTO.postContentImage())
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));

        return PostResponseDTO.toResponseDTO(post);
    }
}
