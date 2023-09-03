package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MakerService {

    private final MakerRepository makerRepository;

    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    @Transactional
    public MakerResponseDTO signUpMaker(MakerRequestDTO dto) {
        Maker maker = Maker.builder()
                .makerName(dto.makerName())
                .makerBrand(dto.makerBrand())
                .makerEmail(dto.makerEmail())
                .build();

        Maker savedMaker = makerRepository.save(maker);

        return MakerResponseDTO.of(savedMaker.getMakerName(), savedMaker.getMakerBrand(), savedMaker.getMakerEmail());
    }

    public MakerResponseDTO getMaker(Long id) {
        Maker findMaker = makerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 Maker가 존재하지 않습니다."));
        return MakerResponseDTO.of(findMaker.getMakerName(), findMaker.getMakerBrand(), findMaker.getMakerEmail());
    }

    public MakerResponseDTO updateMaker(
            Long id,
            MakerRequestDTO dto
    ) {

        Maker maker = makerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 Maker가 존재하지 않습니다."));

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
}