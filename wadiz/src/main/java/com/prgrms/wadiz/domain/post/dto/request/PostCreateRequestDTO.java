package com.prgrms.wadiz.domain.post.dto.request;

import lombok.Builder;

@Builder
public record PostCreateRequestDTO(
        String postTitle,
        String postDescription,
        String postThumbNailImage,
        String postContentImage
) {
}
