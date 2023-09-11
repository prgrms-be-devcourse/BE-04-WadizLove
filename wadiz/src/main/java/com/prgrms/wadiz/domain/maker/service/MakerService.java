package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.MakerServiceDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.respository.MakerRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MakerService {
    private final MakerRepository makerRepository;

    private JPAQueryFactory jpaQueryFactory;

    @Transactional
    public MakerResponseDTO signUpMaker(MakerCreateRequestDTO dto) {
        checkDuplicateName(dto.makerName());
        checkDuplicateEmail(dto.makerEmail());

        Maker maker = Maker.builder()
                .makerName(dto.makerName())
                .makerBrand(dto.makerBrand())
                .makerEmail(dto.makerEmail())
                .build();

        makerRepository.save(maker);

        return MakerResponseDTO.from(maker);
    }

    @Transactional
    public MakerServiceDTO getMakerDTO(Long makerId) {
        Maker retrivedMaker = makerRepository.findById(makerId)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));

        return MakerServiceDTO.from(retrivedMaker);
    }

    @Transactional
    public MakerResponseDTO updateMaker(
            Long makerId,
            MakerUpdateRequestDTO dto
    ) {
        checkDuplicateName(dto.makerName());
        checkDuplicateEmail(dto.makerEmail());

        Maker maker = makerRepository.findById(makerId)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));

        maker.updateMaker(
                dto.makerName(),
                dto.makerEmail(),
                dto.makerBrand()
        );

        return MakerResponseDTO.of(
                maker.getMakerName(),
                maker.getMakerBrand(),
                maker.getMakerEmail()
        );
    }

    @Transactional
    public void deleteMaker(Long makerId) {
        Maker maker = makerRepository.findById(makerId)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));

        maker.unregisteredMaker();
    }

    public void checkDuplicateEmail(String email) {
        if(makerRepository.existsByMakerEmail(email)){
            throw new BaseException((ErrorCode.DUPLICATED_EMAIL));
        }
    }
    public void checkDuplicateName(String name) {
        if(makerRepository.existsByMakerName(name)){
            throw new BaseException((ErrorCode.DUPLICATED_NAME));
        }
    }
}