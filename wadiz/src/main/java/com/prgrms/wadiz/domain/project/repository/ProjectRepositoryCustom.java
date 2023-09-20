package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.PagingDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<PagingDTO> findAllByCondition(String cursorId, ProjectSearchCondition projectSearchCondition, Pageable pageable);
}
