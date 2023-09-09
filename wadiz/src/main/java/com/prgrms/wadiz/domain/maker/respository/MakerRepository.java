package com.prgrms.wadiz.domain.maker.respository;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakerRepository extends JpaRepository<Maker, Long> {
    boolean existsByMakerEmail(String email);
}
