package com.prgrms.wadiz.domain.maker.service;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.maker.repository.MakerRepository;
import org.springframework.stereotype.Service;

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
}
