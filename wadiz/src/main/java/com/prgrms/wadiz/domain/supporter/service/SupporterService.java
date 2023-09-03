package com.prgrms.wadiz.domain.supporter.service;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterRequestDTO;
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
    public SupporterResponseDTO signUpSupporter(SupporterRequestDTO dto) {
        Supporter supporter = Supporter.builder()
                .name(dto.name())
                .email(dto.email())
                .build();

        Supporter savedEntity = supporterRepository.save(supporter);
        return SupporterResponseDTO.of(savedEntity.getSupporterName(),savedEntity.getSupporterEmail());
    }

    public SupporterResponseDTO getSupporter(Long id) {
        Supporter supporter =supporterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 id에 해당하는 서포터가 없습니다"));

        return SupporterResponseDTO.of(supporter.getSupporterName(),supporter.getSupporterEmail());
    }

    public SupporterResponseDTO updateSupporter(Long id, SupporterRequestDTO dto) {
        Supporter supporter =supporterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 id에 해당하는 서포터가 없습니다"));

        supporter.changeName(dto.name());
        supporter.changeEmail(dto.email());

        Supporter savedSupporter = supporterRepository.save(supporter);
        return SupporterResponseDTO.of(savedSupporter.getSupporterName(),savedSupporter.getSupporterEmail());
    }


    @Transactional
    public void deleteSupporter(Long id) {
        supporterRepository.deleteById(id);
    }

}