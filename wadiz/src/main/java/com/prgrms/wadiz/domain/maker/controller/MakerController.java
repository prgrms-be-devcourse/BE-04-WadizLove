package com.prgrms.wadiz.domain.maker.controller;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "메이커 회원가입", description = "이름, 브랜드, 이름을 입력하여 메아커 회원가입을 한다.")
    @PostMapping("/sign-up")
    public Long signUpMaker(@RequestBody @Valid MakerCreateRequestDTO dto) {
        return makerService.signUpMaker(dto);
    }

    @Operation(summary = "메이커 정보수정", description = "id를 통해 메이커를 조회한 후, 메아커 정보 수정을 한다.")
    @PutMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> updateMaker(
            @PathVariable Long makerId,
            @RequestBody @Valid MakerUpdateRequestDTO dto
    ) {
        makerService.updateMaker(makerId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @Operation(summary = "메이커 탈퇴", description = "id를 통해 메이커를 조회한 후, 메아커 탈퇴를 한다.")
    @DeleteMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> deleteMaker(@PathVariable Long makerId) {
        makerService.deleteMaker(makerId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @Operation(summary = "메이커 조회", description = "id를 통해 메이커를 조회한다.")
    @GetMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> getMaker(@PathVariable Long makerId) {
        MakerResponseDTO makerResponseDTO = makerService.getMaker(makerId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(makerResponseDTO));
    }
}
