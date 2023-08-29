package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterCreateResponseDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import org.springframework.stereotype.Service;

@Service
public class SupporterService {

    private SupporterRepository supporterRepository;


    public SupporterService(SupporterRepository supporterRepository) {
        this.supporterRepository = supporterRepository;
    }

    public SupporterCreateResponseDTO createSupporter(SupporterCreateRequestDTO dto) {
        Supporter entity = dto.toEntity();
        Supporter savedEntity = supporterRepository.save(entity);
        return Supporter.toDTOForResponse(savedEntity);
    }

}
