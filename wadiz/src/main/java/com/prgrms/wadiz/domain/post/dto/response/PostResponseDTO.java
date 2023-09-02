package com.prgrms.wadiz.domain.post.dto.response;

import com.prgrms.wadiz.domain.post.entity.Post;
import lombok.Builder;

@Builder
public record PostResponseDTO(
        Long postId,
        String postTitle,
        String postDescription,
        String postThumbNailImage,
        String postContentImage
) {

    public static PostResponseDTO toResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .postThumbNailImage(post.getPostThumbNailImage())
                .postContentImage(post.getPostContentImage())
                .build();
    }
}
