package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> findAllByCondition(Long cursorId, ProjectSearchCondition projectSearchCondition, Pageable pageable);
}
