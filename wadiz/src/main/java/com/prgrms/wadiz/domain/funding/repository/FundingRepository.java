package com.prgrms.wadiz.domain.funding.repository;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    @Query("SELECT f FROM Funding f WHERE f.project.projectId = :projectId")
    Optional<Funding> findByProjectId(@Param("projectId") Long projectId);

    @Modifying
    @Query("DELETE FROM Funding f WHERE f.project.projectId = :projectId")
    void deleteByProjectId(@Param("projectId") Long projectId);
}
