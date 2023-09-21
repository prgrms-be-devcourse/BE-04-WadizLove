package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupporterService {
    private final SupporterRepository supporterRepository;

    /**
     *
     * Supporter 회원가입
     */
    @Transactional
    public Long signUpSupporter(SupporterCreateRequestDTO dto) {
        checkDuplicateEmail(dto.supporterEmail());
        checkDuplicateName(dto.supporterName());

        Supporter supporter = Supporter.builder()
                .supporterName(dto.supporterName())
                .supporterEmail(dto.supporterEmail())
                .build();

        Supporter savedSupporter = supporterRepository.save(supporter);

        return savedSupporter.getSupporterId();
    }

    /**
     *
     * Supporter id로 서포터 조회 (단건)
     */
    @Transactional(readOnly = true)
    public SupporterResponseDTO getSupporter(Long supporterId) {
        Supporter supporter =supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.warn("Supporter {} is not found", supporterId);

                    throw  new BaseException(ErrorCode.SUPPORTER_NOT_FOUND);
                });

        return SupporterResponseDTO.of(
                supporter.getSupporterName(),
                supporter.getSupporterEmail()
        );
    }

    /**
     *
     * Supporter 정보 수정
     */
    @Transactional
    public SupporterResponseDTO updateSupporter(
            Long supporterId,
            SupporterUpdateRequestDTO dto
    ) {

        checkDuplicateEmail(dto.supporterEmail());
        checkDuplicateName(dto.supporterName());

        Supporter supporter = supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.warn("Supporter {} is not found", supporterId);

                    throw new BaseException(ErrorCode.SUPPORTER_NOT_FOUND);
                });

        supporter.updateSupporter(
                dto.supporterName(),
                dto.supporterEmail()
        );

        return SupporterResponseDTO.of(
                supporter.getSupporterName(),
                supporter.getSupporterEmail()
        );
    }

    /**
     *
     * Supporter 탈퇴
     */
    @Transactional
    public void deleteSupporter(Long supporterId) {
        Supporter supporter = supporterRepository.findById(supporterId)
                .orElseThrow(() -> {
                    log.warn("Supporter {} is not found", supporterId);

                    throw new BaseException(ErrorCode.SUPPORTER_NOT_FOUND);
                });

        supporter.unregisteredSupporter();
    }

    /**
     *
     * Supporter 회원가입시 중복 이메일 검증
     */
    public void checkDuplicateEmail(String email) {
        if(supporterRepository.existsBySupporterEmail(email)){
            log.warn("Duplicate email exists.");

            throw new BaseException((ErrorCode.DUPLICATED_EMAIL));
        }
    }

    /**
     *
     * Supporter 회원가입시 중복 이름 검증
     */
    public void checkDuplicateName(String name) {
        if(supporterRepository.existsBySupporterName(name)){
            log.warn("Duplicate name exists.");

            throw new BaseException((ErrorCode.DUPLICATED_NAME));
        }
    }

}