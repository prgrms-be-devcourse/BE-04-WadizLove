package com.prgrms.wadiz.domain.maker.controller;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "메이커 API")
@RestController
@RequestMapping("/api/makers")
@RequiredArgsConstructor
public class MakerController {

    private final MakerService makerService;

    @ApiOperation(
            value = "회원 가입",
            notes = "메이커의 이름, 브랜드, 이메일을 받아 회원가입을 한다."
    )
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate> signUpMaker(@RequestBody @Valid MakerCreateRequestDTO dto) {
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(dto);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(makerResponseDTO));
    }

    @ApiOperation(
            value = "메이커 정보 수정",
            notes = "메이커의 id를 통해 메이커를 조회한 후, 메이커 정보를 수정한다."
    )
    @PutMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> updateMaker(
            @PathVariable Long makerId,
            @RequestBody @Valid MakerUpdateRequestDTO dto
    ) {
        makerService.updateMaker(makerId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiOperation(
            value = "메이커 탈퇴",
            notes = "메이커의 ID를 통해 메이커를 조회한 후, 해당 메이커를 탈퇴한다."
    )
    @DeleteMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> deleteMaker(@PathVariable Long makerId) {
        makerService.deleteMaker(makerId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiOperation(
            value = "메이커 조회",
            notes = "메이커의 ID를 통해 메이커를 조회한다."
    )
    @GetMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> getMaker(@PathVariable Long makerId) {
        MakerResponseDTO makerResponseDTO = makerService.getMaker(makerId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(makerResponseDTO));
    }
}
