package com.prgrms.wadiz.domain.supporter.controller;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.service.SupporterService;
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

@Tag(name = "supporters", description = "서포터 API")
@RestController
@RequestMapping("/api/supporters")
@RequiredArgsConstructor
public class SupporterController {

    private final SupporterService supporterService;

    /**
     * Supporter 회원가입
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "서포터 회원가입 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "서포터 회원가입 실패"
            )
    })
    @Operation(
            summary = "서포터 회원가입",
            description = "서포터 요청 양식(SupporterCreateRequestDTO)을 이용하여 서포터 회원가입을 합니다."
    )
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Supporter")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate> signUpSupporter(@RequestBody @Valid SupporterCreateRequestDTO dto) {
        Long supporterId = supporterService.signUpSupporter(dto);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(supporterId));
    }

    /**
     * Supporter update
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "서포터 정보 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "서포터 정보 수정 실패"
            )
    })
    @Operation(
            summary = "서포터 정보 수정",
            description = "서포터 id를 이용하여 서포터를 조회한 후, 서포터 정보를 수정합니다."
    )
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Supporter")
    @PutMapping(value = "/{supporterId}")
    public ResponseEntity<ResponseTemplate> updateSupporter(
            @Parameter(description = "서포터 id") @PathVariable Long supporterId,
            @RequestBody @Valid SupporterUpdateRequestDTO dto
    ) {
        supporterService.updateSupporter(supporterId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Supporter 탈퇴
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "서포터 탈퇴 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "서포터 탈퇴 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Supporter")
    @Operation(
            summary = "서포터 탈퇴",
            description = "서포터 id를 이용하여 서포터를 조회한 후, 해당 서포터를 탈퇴합니다."
    )
    @DeleteMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> deleteSupporter(@Parameter(description = "서포터 id") @PathVariable Long supporterId){
        supporterService.deleteSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * Supporter 단건 조회
     */
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "서포터 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "서포터 조회 실패"
            )
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Supporter")
    @Operation(
            summary = "서포터 조회",
            description = "서포터 id를 이용하여 서포터를 조회합니다."
    )
    @GetMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> getSupporter(@Parameter(description = "서포터 id") @PathVariable Long supporterId) {
        SupporterResponseDTO supporterResponseDTO = supporterService.getSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(supporterResponseDTO));
    }
}
