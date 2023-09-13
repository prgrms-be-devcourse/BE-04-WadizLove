package com.prgrms.wadiz.domain.supporter.controller;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.service.SupporterService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "서포터 API")
@RestController
@RequestMapping("/api/supporters")
@RequiredArgsConstructor
public class SupporterController {

    private final SupporterService supporterService;

    @ApiOperation(
            value = "회원 가입",
            notes = "서포터의 이름, 이메일을 받아 회원가입을 한다."
    )
    @PostMapping("/sign-up") //TODO : id값 반환해주기
    public ResponseEntity<ResponseTemplate> signUpSupporter(@RequestBody @Valid SupporterCreateRequestDTO dto) {
        supporterService.signUpSupporter(dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiOperation(
            value = "서포터 정보 수정",
            notes = "서포터의 id를 통해 서포터를 조회한 후, 서포터 정보를 수정한다."
    )
    @PutMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> updateSupporter(
            @PathVariable Long supporterId,
            @RequestBody @Valid SupporterUpdateRequestDTO dto
    ) {
        supporterService.updateSupporter(supporterId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiOperation(
            value = "서포터 탈퇴",
            notes = "서포터의 ID를 통해 서포터를 조회한 후, 해당 서포터를 탈퇴한다."
    )
    @DeleteMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> deleteSupporter(@PathVariable Long supporterId){
        supporterService.deleteSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiOperation(
            value = "서포터 조회",
            notes = "서포터의 ID를 통해 서포터를 조회한다."
    )
    @GetMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> getSupporter(@PathVariable Long supporterId) {
        SupporterResponseDTO supporterResponseDTO = supporterService.getSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(supporterResponseDTO));
    }
}
