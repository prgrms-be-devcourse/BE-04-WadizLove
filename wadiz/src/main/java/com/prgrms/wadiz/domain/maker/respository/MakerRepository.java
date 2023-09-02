package com.prgrms.wadiz.domain.maker.repository;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakerRepository extends JpaRepository<Maker, Long> {
}
