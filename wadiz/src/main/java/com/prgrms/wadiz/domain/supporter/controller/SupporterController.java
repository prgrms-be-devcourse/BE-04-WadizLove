package com.prgrms.wadiz.domain.supporter.controller;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.service.SupporterService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supporters")
@RequiredArgsConstructor
public class SupporterController {

    private final SupporterService supporterService;

    @PostMapping
    public ResponseEntity<ResponseTemplate> signUpSupporter(SupporterRequestDTO dto) {
        supporterService.signUpSupporter(dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @PutMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> updateSupporter(
            @PathVariable Long supporterId,
            SupporterRequestDTO dto
    ) {
        supporterService.updateSupporter(supporterId, dto);
        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @GetMapping("/{supporterId}")
    public ResponseEntity<ResponseTemplate> getSupporter(@PathVariable Long supporterId) {
        SupporterResponseDTO supporterResponseDTO = supporterService.getSupporter(supporterId);
        return ResponseEntity.ok(ResponseFactory.getSingleResult(supporterResponseDTO));
    }
}
