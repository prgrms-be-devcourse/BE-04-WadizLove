package com.prgrms.wadiz.domain.funding.repository;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> {
}
