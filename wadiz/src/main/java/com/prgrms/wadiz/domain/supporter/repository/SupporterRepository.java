package com.prgrms.wadiz.domain.supporter.repository;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupporterRepository extends JpaRepository<Supporter, Long> {
    boolean existsBySupporterEmail(String email);

    boolean existsBySupporterName(String name);
}