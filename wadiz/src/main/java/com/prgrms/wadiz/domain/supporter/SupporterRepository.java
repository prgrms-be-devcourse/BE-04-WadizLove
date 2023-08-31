package com.prgrms.wadiz.domain.supporter;

import com.prgrms.wadiz.domain.supporter.entity.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupporterRepository extends JpaRepository<Supporter,Long> {
}
