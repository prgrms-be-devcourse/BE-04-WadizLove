package com.prgrms.wadiz.domain.reward.controller;

import com.prgrms.wadiz.domain.reward.dto.request.RewardRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.domain.reward.service.RewardService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @PostMapping("/{projectId]")
    public ResponseEntity<ResponseTemplate> createReward(
            @PathVariable Long projectId,
            RewardRequestDTO dto
    ) {
        RewardResponseDTO rewardResponseDTO = rewardService.createReward(projectId, dto);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    @PutMapping("{rewardId}")
    public ResponseEntity<ResponseTemplate> updateReward(
            @PathVariable Long rewardId,
            RewardRequestDTO dto
    ) {
        RewardResponseDTO rewardResponseDTO = rewardService.updateReward(rewardId, dto);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    @GetMapping("{rewardId}")
    public ResponseEntity<ResponseTemplate> getReward(@PathVariable Long rewardId) {
        RewardResponseDTO rewardResponseDTO = rewardService.getReward(rewardId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }
}
