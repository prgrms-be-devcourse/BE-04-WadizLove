package com.prgrms.wadiz.domain.reward.controller;

import com.prgrms.wadiz.domain.project.service.ProjectUseCase;
import com.prgrms.wadiz.domain.reward.dto.request.RewardCreateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.request.RewardUpdateRequestDTO;
import com.prgrms.wadiz.domain.reward.dto.response.RewardResponseDTO;
import com.prgrms.wadiz.global.annotation.ApiErrorCodeExample;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "rewards", description = "리워드 API")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class RewardController {

    private final ProjectUseCase projectUseCase;

    /**
     * Reward 정보 CURD
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리워드 생성 성공"),
            @ApiResponse(responseCode = "404",
                    description = "리워드 생성 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Reward")
    @Operation(summary = "리워드 생성", description = "프로젝트 id를 받고, 리워드명, 리워드 설명, 재고, 가격, 타입, 상태를 입력하여 리워드를 생성한다.")
    @PostMapping("/{projectId}/rewards")
    public ResponseEntity<ResponseTemplate> createReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @RequestBody @Valid RewardCreateRequestDTO rewardCreateRequestDTO
    ) {
        projectUseCase.createReward(projectId, rewardCreateRequestDTO);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리워드 조회 성공"),
            @ApiResponse(responseCode = "404",
                    description = "리워드 조회 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Reward")
    @Operation(summary = "리워드 조회", description = "프로젝트 id, 리워드 id를 통해 리워드를 조회한다.")
    @GetMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> getReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "리워드 id") @PathVariable Long rewardId
    ){
        RewardResponseDTO rewardResponseDTO = projectUseCase.getReward(projectId, rewardId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리워드 수정 성공"),
            @ApiResponse(responseCode = "404",
                    description = "리워드 수정 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Reward")
    @Operation(summary = "리워드 수정", description = "프로젝트 id, 리워드 id를 통해 리워드를 조회한후 리워드 정보를 수정한다.")
    @PutMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> updateReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "리워드 id") @PathVariable Long rewardId,
            @RequestBody @Valid RewardUpdateRequestDTO dto
    ) {
        projectUseCase.updateReward(projectId,rewardId, dto);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "리워드 삭제 성공"),
            @ApiResponse(responseCode = "404",
                    description = "리워드 삭제 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Reward")
    @Operation(summary = "리워드 삭제", description = "프로젝트 id, 리워드 id를 통해 리워드를 삭제한다.")
    @DeleteMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> deleteReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "리워드 id") @PathVariable Long rewardId) {
        projectUseCase.deleteReward(projectId, rewardId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

}
