package com.prgrms.wadiz.domain.maker.respository;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MakerRepository extends JpaRepository<Maker, Long> {
}
