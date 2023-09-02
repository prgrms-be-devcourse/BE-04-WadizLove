package com.prgrms.wadiz.domain.supporter.dto.request;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.Builder;

@Builder
public record SupporterCreateRequestDTO(String name, String email) {
    public Supporter toEntity() {
        return Supporter.builder()
                .name(name)
                .email(email)
                .build();
    }
}
