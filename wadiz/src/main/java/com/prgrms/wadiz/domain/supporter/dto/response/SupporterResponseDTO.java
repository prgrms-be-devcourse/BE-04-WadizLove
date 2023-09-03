package com.prgrms.wadiz.domain.supporter.dto.response;


import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.Builder;

@Builder
public record SupporterResponseDTO(String name, String email) {
    public static SupporterResponseDTO of(String name, String email) {
        return SupporterResponseDTO.builder()
                .name(name)
                .email(email)
                .build();
    }
}