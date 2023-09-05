package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.MakerServiceDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerUpdateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.respository.MakerRepository;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MakerService {
    private final MakerRepository makerRepository;

    @Transactional
    public MakerResponseDTO signUpMaker(MakerCreateRequestDTO dto) {
        Maker maker = Maker.builder()
                .makerName(dto.makerName())
                .makerBrand(dto.makerBrand())
                .makerEmail(dto.makerEmail())
                .build();

        Maker savedMaker = makerRepository.save(maker);

        return MakerResponseDTO.of(savedMaker.getMakerName(), savedMaker.getMakerBrand(), savedMaker.getMakerEmail());
    }

    @Transactional(readOnly = true)
    public MakerResponseDTO getMaker(Long id) {
        Maker findMaker = makerRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));
        return MakerResponseDTO.of(findMaker.getMakerName(), findMaker.getMakerBrand(), findMaker.getMakerEmail());
    }

    @Transactional
    public MakerResponseDTO updateMaker(
            Long id,
            MakerUpdateRequestDTO dto
    ) {

        Maker maker = makerRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));

        maker.changeMakerName(dto.makerName());
        maker.chaneMakerBrand(dto.makerBrand());
        maker.changeMakerEmail(dto.makerEmail());

        Maker savedMaker = makerRepository.save(maker);
        return MakerResponseDTO.of(savedMaker.getMakerName(), savedMaker.getMakerBrand(), savedMaker.getMakerEmail());
    }

    @Transactional
    public void deleteMaker(Long id) {
        makerRepository.deleteById(id);
    }

    public MakerServiceDTO getMakerDTO(Long makerId) {
        Maker findMaker = makerRepository.findById(makerId)
                .orElseThrow(() -> new BaseException(ErrorCode.MAKER_NOT_FOUND));
        return MakerServiceDTO.from(findMaker);
    }
}