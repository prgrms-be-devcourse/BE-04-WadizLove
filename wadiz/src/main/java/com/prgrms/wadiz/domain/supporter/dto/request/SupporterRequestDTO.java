package com.prgrms.wadiz.domain.supporter.dto.request;

import lombok.Builder;

@Builder
public record SupporterRequestDTO(String name, String email) {
}
