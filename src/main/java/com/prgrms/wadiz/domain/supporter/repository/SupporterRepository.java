package com.prgrms.wadiz.domain.supporter.repository;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupporterRepository extends JpaRepository<Supporter, Long> {
}
