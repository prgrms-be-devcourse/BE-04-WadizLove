package com.prgrms.wadiz.domain.project.dto.request;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import lombok.Getter;

@Getter
public record ProjectCreateRequestDTO(
        Long makerId,
        PostCreateRequestDTO postCreateRequestDTO,
        FundingCreateRequestDTO fundingCreateRequestDTO,
        RewardCreateRequestDTO rewardCreateRequestDTO
) {
}
