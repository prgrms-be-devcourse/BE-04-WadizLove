package com.prgrms.wadiz.domain.supporter.dto.response;


import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.Builder;

@Builder
public record SupporterResponseDTO(Long id, String name, String email) {
    public Supporter toEntity() {
        return Supporter.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
    }
}
