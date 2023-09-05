package com.prgrms.wadiz.domain.maker.controller;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
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
@RequestMapping("/makers")
@RequiredArgsConstructor
public class MakerController {

    private final MakerService makerService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseTemplate> signUpMaker(MakerCreateRequestDTO dto) {
        makerService.signUpMaker(dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @PutMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> updateMaker(@PathVariable Long makerId, MakerUpdateRequestDTO dto) {
        makerService.updateMaker(makerId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @DeleteMapping("/{makerId}")
    public ResponseEntity<ResponseTemplate> deleteMaker(@PathVariable Long makerId) {
        makerService.deleteMaker(makerId);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

}
