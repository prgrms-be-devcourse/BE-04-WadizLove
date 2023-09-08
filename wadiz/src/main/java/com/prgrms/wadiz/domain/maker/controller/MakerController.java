package com.prgrms.wadiz.domain.maker.controller;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.service.MakerService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/makers")
@RequiredArgsConstructor
public class MakerController {

    private final MakerService makerService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate> signUpMaker(@RequestBody @Valid MakerCreateRequestDTO dto) {
        MakerResponseDTO makerResponseDTO = makerService.signUpMaker(dto);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(makerResponseDTO));
    }

    @PutMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> updateMaker(
            @PathVariable Long makerId,
            @RequestBody @Valid MakerUpdateRequestDTO dto
    ) {
        makerService.updateMaker(makerId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> deleteMaker(@PathVariable Long makerId) {
        makerService.deleteMaker(makerId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    //TODO : GET 메소드 만들기

}
