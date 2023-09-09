package com.prgrms.wadiz.domain.maker.dto;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record MakerServiceDTO(
        Long makerId,

        @Size(min = 2, message = "이름은 최소 2자 이상입니다.")
        String makerName,

        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @Email(message = "이메일을 입렬해주세요.")
        String makerEmail
) {
    public static Maker toEntity(MakerServiceDTO dto) {

        return Maker.builder()
                .makerId(dto.makerId())
                .makerName(dto.makerName())
                .makerBrand(dto.makerBrand())
                .makerEmail(dto.makerEmail())
                .build();
    }

    public static MakerServiceDTO from(Maker findMaker) {
        return MakerServiceDTO.builder()
                .makerId(findMaker.getMakerId())
                .makerName(findMaker.getMakerName())
                .makerBrand(findMaker.getMakerBrand())
                .makerEmail(findMaker.getMakerEmail())
                .build();
    }
}
