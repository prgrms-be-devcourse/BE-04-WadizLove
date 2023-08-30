package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.request.MakerModifyRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MakerService {

    private final MakerRepository makerRepository;

    public MakerService(MakerRepository makerRepository) {
        this.makerRepository = makerRepository;
    }

    public MakerResponseDTO signUpMaker(MakerCreateRequestDTO dto) {
        Maker maker = makerRepository.save(dto.toEntity());
        return Maker.toDTOForResponse(maker);
    }

    public MakerResponseDTO getMaker(Long id) {
        Maker findMaker = makerRepository.findById(id).orElseThrow(() -> new RuntimeException("id에 해당하는 Maker가 존재하지 않습니다."));
        return Maker.toDTOForResponse(findMaker);
    }

    public MakerResponseDTO modifyMaker(Long id, MakerModifyRequestDTO dto) {
        MakerResponseDTO makerDTO = getMaker(id);
        Maker maker = makerDTO.toEntity();
        maker.changeMakerName(dto.makerName());
        maker.chaneMakerBrand(dto.makerBrand());
        maker.changeMakerEmail(dto.makerEmail());

        Maker save = makerRepository.save(maker);
        return Maker.toDTOForResponse(save);
    }

    public void deleteMaker(Long id) {
        makerRepository.deleteById(id);
    }
}
