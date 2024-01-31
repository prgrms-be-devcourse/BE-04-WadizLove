package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long>, ProjectRepositoryCustom {
}
