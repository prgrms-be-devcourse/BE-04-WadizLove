package com.prgrms.wadiz.domain.post.dto.response;

import com.prgrms.wadiz.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record PostResponseDTO(
        Long postId,
        String postTitle,
        String postDescription,
        String postThumbNail,
        String postContentImage
) {

    public static PostResponseDTO toResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .postThumbNail(post.getPostThumbNailImage())
                .postContentImage(post.getPostContentImage())
                .build();
    }
}
