package com.prgrms.wadiz.domain.maker.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MakerCreateRequestDTO(
        String makerName,
        String makerBrand,
        String makerEmail) {
}