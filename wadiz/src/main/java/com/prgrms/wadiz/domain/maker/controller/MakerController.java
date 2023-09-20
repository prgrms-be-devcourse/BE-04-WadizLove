package com.prgrms.wadiz.domain.maker.controller;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.global.annotation.ApiErrorCodeExample;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "makers", description = "메이커 API")
@RestController
@RequestMapping("/api/makers")
@RequiredArgsConstructor
public class MakerController {

    private final MakerService makerService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "메이커 회원가입 성공"),
            @ApiResponse(responseCode = "404",
                    description = "메이커 회원가입 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Maker")
    @Operation(summary = "메이커 회원가입", description = "이름, 브랜드, 이름을 입력하여 메아커 회원가입을 한다.")
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate> signUpMaker(@RequestBody @Valid MakerCreateRequestDTO dto) {
        makerService.signUpMaker(dto);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "메이커 정보 수정 성공"),
            @ApiResponse(responseCode = "404",
                    description = "메이커 정보 수정 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Maker")
    @Operation(summary = "메이커 정보수정", description = "id를 통해 메이커를 조회한 후, 메아커 정보 수정을 한다.")
    @PutMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> updateMaker(
            @Parameter(description = "메이커 id") @PathVariable Long makerId,
            @RequestBody @Valid MakerUpdateRequestDTO dto
    ) {
        makerService.updateMaker(makerId, dto);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "메이커 탈퇴 성공"),
            @ApiResponse(responseCode = "404",
                    description = "메이커 탈퇴 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Maker")
    @Operation(summary = "메이커 탈퇴", description = "id를 통해 메이커를 조회한 후, 메아커 탈퇴를 한다.")
    @Parameter(name = "makerId", description = "메이커 id", in = ParameterIn.PATH)
    @DeleteMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> deleteMaker(@PathVariable Long makerId) {
        makerService.deleteMaker(makerId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "메이커 조회 성공"),
            @ApiResponse(responseCode = "404",
                    description = "메이커 조회 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Maker")
    @Operation(summary = "메이커 조회", description = "id를 통해 메이커를 조회한다.")
    @Parameter(name = "makerId", description = "메이커 id", in = ParameterIn.PATH)
    @GetMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> getMaker(@PathVariable Long makerId) {
        MakerResponseDTO makerResponseDTO = makerService.getMaker(makerId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(makerResponseDTO));
    }
}
