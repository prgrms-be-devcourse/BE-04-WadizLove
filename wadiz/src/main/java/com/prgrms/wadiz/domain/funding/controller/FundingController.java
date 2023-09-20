package com.prgrms.wadiz.domain.funding.controller;

import com.prgrms.wadiz.domain.funding.dto.request.FundingCreateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.request.FundingUpdateRequestDTO;
import com.prgrms.wadiz.domain.funding.dto.response.FundingResponseDTO;
import com.prgrms.wadiz.domain.project.service.ProjectUseCase;
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
        name = "fundings",
        description = "펀딩 API"
)
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class FundingController {

    private final ProjectUseCase projectUseCase;

    /**
     * Funding create
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "펀딩 생성 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "펀딩 생성 실패"
            )
    })
  
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Funding"
    )
    @Operation(
            summary = "펀딩 생성",
            description = "프로젝트 id와, 펀딩 요청 양식(FundingCreateRequestDTO)을 이용하여 펀딩을 생성힙니다."
    )
    @PostMapping("/{projectId}/fundings/new")
    public ResponseEntity<ResponseTemplate> createFunding(
            @Parameter(description = "프로젝트 id")
            @PathVariable Long projectId,
            @RequestBody @Valid FundingCreateRequestDTO fundingCreateRequestDTO
    ) {
        Long fundingId = projectUseCase.createFunding(
                projectId, fundingCreateRequestDTO
        );

        return ResponseEntity.ok(ResponseFactory.getSingleResult(fundingId));
    }

    /**
     * Funding 단건 조회
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "펀딩 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "펀딩 조회 실패"
            )
    })
  
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Funding"
    )
    @Operation(
            summary = "펀딩 조회",
            description = "프로젝트 id를 이용하여 펀딩을 조회합니다."
    )
    @GetMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> getFunding(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId
    ) {
        FundingResponseDTO fundingResponseDTO = projectUseCase.getFunding(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(fundingResponseDTO));
    }

    /**
     * Funding update
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "펀딩 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "펀딩 수정 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Funding"
    )
    @Operation(
            summary = "펀딩 수정",
            description = "프로젝트 id를 이용하여 펀딩을 조회한 후, 펀딩 정보를 수정합니다."
    )
    @PutMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> updateFunding(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @RequestBody @Valid FundingUpdateRequestDTO fundingUpdateRequestDTO
    ) {
        projectUseCase.updateFunding(
                projectId,
                fundingUpdateRequestDTO
        );

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Funding delete
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "펀딩 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "펀딩 삭제 실패"
            )
    })
    @ApiErrorCodeExample(
            value = ErrorCode.class,
            domain = "Funding"
    )
    @Operation(
            summary = "펀딩 삭제",
            description = "프로젝트 id를 이용하여 펀딩을 조회한 후, 펀딩을 삭제합니다."
    )
    @DeleteMapping("/{projectId}/fundings")
    public ResponseEntity<ResponseTemplate> deleteFunding(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId
    ) {
        projectUseCase.deleteFunding(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

}
