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

@Tag(
        name = "rewards",
        description = "리워드 API"
)
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class RewardController {
    private final ProjectUseCase projectUseCase;
    /**
     * Reward create
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "리워드 생성 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리워드 생성 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Reward"
    )
    @Operation(
            summary = "리워드 생성",
            description = "프로젝트 id와, 리워드 요청 양식(RewardCreateRequestDTO)을 이용하여 리워드를 생성한다."
    )
    @PostMapping("/{projectId}/rewards")
    public ResponseEntity<ResponseTemplate> createReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @RequestBody @Valid RewardCreateRequestDTO rewardCreateRequestDTO
    ) {
        Long rewardId = projectUseCase.createReward(
                projectId,
                rewardCreateRequestDTO
        );

        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardId));
    }

    /**
     * Reward 단건 조회
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "리워드 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리워드 조회 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Reward"
    )
    @Operation(
            summary = "리워드 조회",
            description = "프로젝트 id, 리워드 id를 이용하여 리워드를 조회합니다."
    )
    @GetMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> getReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "리워드 id") @PathVariable Long rewardId
    ){
        RewardResponseDTO rewardResponseDTO = projectUseCase.getReward(
                projectId,
                rewardId
        );

        return ResponseEntity.ok(ResponseFactory.getSingleResult(rewardResponseDTO));
    }

    /**
     * Reward update
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "리워드 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리워드 수정 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Reward"
    )
    @Operation(
            summary = "리워드 수정",
            description = "프로젝트 id, 리워드 id를 통해 리워드를 조회한 후 리워드 정보를 수정합니다."
    )
    @PutMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> updateReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "리워드 id") @PathVariable Long rewardId,
            @RequestBody @Valid RewardUpdateRequestDTO dto
    ) {
        projectUseCase.updateReward(
                projectId,
                rewardId,
                dto
        );

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Reward delete
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "리워드 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "리워드 삭제 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Reward"
    )
    @Operation(
            summary = "리워드 삭제",
            description = "프로젝트 id, 리워드 id를 이용하여 리워드를 삭제합니다."
    )
    @DeleteMapping("/{projectId}/rewards/{rewardId}")
    public ResponseEntity<ResponseTemplate> deleteReward(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "리워드 id") @PathVariable Long rewardId
    ) {
        projectUseCase.deleteReward(projectId, rewardId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

}
