package com.prgrms.wadiz.domain.supporter.dto.request;

import lombok.Builder;

@Builder
public record SupporterCreateRequestDTO(
        String name,
        String email) {
}
