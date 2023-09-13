package com.prgrms.wadiz.domain.maker.dto;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import io.swagger.annotations.ApiParam;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
public record MakerServiceDTO(
        @ApiParam(value = "메이커 아이디")
        Long makerId,

        @ApiParam(value = "메이커 이름")
        @Size(min = 2, message = "이름은 최소 2자 이상입니다.")
        String makerName,

        @ApiParam(value = "메이커 브랜드")
        @NotBlank(message = "브랜드를 입력해주세요.")
        String makerBrand,

        @ApiParam(value = "메이커 이메일")
        @Email(message = "이메일 형식이 맞지 않습니다.")
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
