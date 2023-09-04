package com.prgrms.wadiz.domain.maker.dto.request;

import lombok.Builder;

@Builder
public record MakerUpdateRequestDTO(
        String makerName,
        String makerBrand,
        String makerEmail
) {
}
