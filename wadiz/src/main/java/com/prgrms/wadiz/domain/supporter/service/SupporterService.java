package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.request.SupporterUpdateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import com.prgrms.wadiz.domain.supporter.repository.SupporterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SupporterService {

    private SupporterRepository supporterRepository;

    public SupporterService(SupporterRepository supporterRepository) {
        this.supporterRepository = supporterRepository;
    }

    @Transactional

    public SupporterResponseDTO createSupporter(SupporterCreateRequestDTO dto) {
        Supporter entity = dto.toEntity();
        Supporter savedEntity = supporterRepository.save(entity);
        return Supporter.toDTOForResponse(savedEntity);
    }

    public SupporterResponseDTO getSupporter(Long id) {
        Supporter supporter =supporterRepository.findById(id).orElseThrow(() -> new RuntimeException("수정할 id에 해당하는 서포터가 없습니다"));
        return Supporter.toDTOForResponse(supporter);
    }

    public SupporterResponseDTO updateSupporter(Long id, SupporterUpdateRequestDTO dto) {
        Supporter supporter =supporterRepository.findById(id).orElseThrow(() -> new RuntimeException("수정할 id에 해당하는 서포터가 없습니다"));
        supporter.changeName(dto.name());
        supporter.changeEmail(dto.email());
        Supporter savedSupporter = supporterRepository.save(supporter);
        return Supporter.toDTOForResponse(savedSupporter);
    }


    @Transactional
    public void deleteSupporter(Long id) {
        supporterRepository.deleteById(id);
    }

}