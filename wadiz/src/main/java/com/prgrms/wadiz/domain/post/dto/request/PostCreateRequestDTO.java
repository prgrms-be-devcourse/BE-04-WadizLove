package com.prgrms.wadiz.domain.post.dto.request;

import lombok.Getter;

@Getter
public record PostCreateRequestDTO(
        String postTitle,
        String PostDescription,
        String postThumbNail,
        String postContentImage
) {
}
