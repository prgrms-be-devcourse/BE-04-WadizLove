package com.prgrms.wadiz.domain.post.dto.request;

public record PostUpdateRequestDTO(
        String postTitle,
        String postDescription,
        String postThumbNailImage,
        String postContentImage
) {
}
