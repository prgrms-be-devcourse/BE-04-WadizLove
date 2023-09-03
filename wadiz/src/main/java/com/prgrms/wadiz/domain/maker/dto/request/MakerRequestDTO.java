package com.prgrms.wadiz.domain.maker.dto.request;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import lombok.Builder;

@Builder
public record MakerRequestDTO(
        String makerName,
        String makerBrand,
        String makerEmail) {
}