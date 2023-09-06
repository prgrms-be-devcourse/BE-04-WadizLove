package com.prgrms.wadiz.domain.supporter.dto.response;


import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import lombok.Builder;

@Builder
public record SupporterResponseDTO(String supporterName,
                                   String supporterEmail) {
    public static SupporterResponseDTO of(String name, String email) {
        return SupporterResponseDTO.builder()
                .supporterName(name)
                .supporterEmail(email)
                .build();
    }
}