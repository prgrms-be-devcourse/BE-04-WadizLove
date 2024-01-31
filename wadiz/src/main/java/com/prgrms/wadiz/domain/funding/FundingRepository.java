package com.prgrms.wadiz.domain.funding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundingRepository extends JpaRepository<Funding, Long> {
//    @Query("SELECT f FROM Funding f WHERE f.project.projectId = :projectId")
//    Optional<Funding> findByProjectId(@Param("projectId") Long projectId);

    Optional<Funding> findByProject_ProjectId(Long projectId);


    //    @Modifying
//    @Query("DELETE FROM Funding f WHERE f.project.projectId = :projectId")
//    void deleteByProjectId(@Param("projectId") Long projectId);
    void deleteByProject_ProjectId(Long projectId); //TODO : 변경가능한데 바꿀 지 상의해보기

    boolean existsByProject_ProjectId(Long projectId);
}
