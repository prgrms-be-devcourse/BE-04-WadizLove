package com.prgrms.wadiz.domain.project.dto.response;

import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectResponseDTO(
        Long projectId,
        MakerResponseDTO makerResponseDTO,
        PostResponseDTO postResponseDTO,
        FundingResponseDTO fundingResponseDTO,
        List<RewardResponseDTO> rewardResponseDTOs
) {
    public static ProjectResponseDTO from(Long projectId) {
        return ProjectResponseDTO.builder()
                .projectId(projectId)
                .build();
    }

    public static ProjectResponseDTO of(
            MakerResponseDTO makerResponseDTO,
            PostResponseDTO postResponseDTO,
            FundingResponseDTO fundingResponseDTO,
            List<RewardResponseDTO> rewardResponseDTOs
    ) {
        return ProjectResponseDTO.builder()
                .makerResponseDTO(makerResponseDTO)
                .postResponseDTO(postResponseDTO)
                .fundingResponseDTO(fundingResponseDTO)
                .rewardResponseDTOs(rewardResponseDTOs)
                .build();
    }
}
