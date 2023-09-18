package com.prgrms.wadiz.domain.supporter.controller;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.service.SupporterService;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "서포터 회원가입 성공"),
            @ApiResponse(responseCode = "404",
                    description = "서포터 회원가입 실패",
                    content = @Content(schema = @Schema(implementation = BaseException.class)))
    })
    @Operation(summary = "서포터 회원가입", description = "이름, 이메일을 입력하여 서포터 회원가입을 한다.")
    @PostMapping("/sign-up")
    public Long signUpSupporter(@RequestBody @Valid SupporterCreateRequestDTO dto) {
        return supporterService.signUpSupporter(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "서포터 정보 수정 성공"),
            @ApiResponse(responseCode = "404",
                    description = "서포터 정보 수정 실패",
                    content = @Content(schema = @Schema(implementation = BaseException.class)))
    })
    @Operation(summary = "서포터 정보 수정", description = "id를 통해 서포터를 조회한 후, 서포터 정보를 수정한다.")
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Supporter")
    @PutMapping(value = "/{supporterId}")
    public ResponseEntity<ResponseTemplate> updateSupporter(
            @PathVariable Long supporterId,
            @RequestBody @Valid SupporterUpdateRequestDTO dto
    ) {
        supporterService.updateSupporter(supporterId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "서포터 탈퇴 성공"),
            @ApiResponse(responseCode = "404",
                    description = "서포터 탈퇴 실패",
                    content = @Content(schema = @Schema(implementation = BaseException.class)))
    })
    @Operation(summary = "서포터 탈퇴", description = "id를 통해 서포터를 조회한 후, 해당 서포터를 탈퇴한다.")
    @DeleteMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> deleteSupporter(@PathVariable Long supporterId){
        supporterService.deleteSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "서포터 조회 성공",
                    content = @Content(schema = @Schema(implementation = SupporterResponseDTO.class))),
            @ApiResponse(responseCode = "404",
                    description = "서포터 조회 실패",
                    content = @Content(schema = @Schema(implementation = BaseException.class)))
    })
    @Operation(summary = "서포터 조회", description = "id를 통해 서포터를 조회한다.")
    @GetMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> getSupporter(@PathVariable Long supporterId) {
        SupporterResponseDTO supporterResponseDTO = supporterService.getSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(supporterResponseDTO));
    }
}
