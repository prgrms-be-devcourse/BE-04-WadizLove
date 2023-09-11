package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectSummaryResponseDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.prgrms.wadiz.domain.project.entity.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProjectSummaryResponseDTO> findAllByCondition(Long cursorId, ProjectSearchCondition projectSearchCondition, Pageable pageable) {
        List<Project> findBoards = jpaQueryFactory.selectFrom(project)
                .where(cursorId(cursorId))
                .limit(pageable.getPageSize())
                .fetch();

        List<ProjectSummaryResponseDTO> responseDTOS = findBoards.stream()
                .map(ProjectSummaryResponseDTO::from)
                .toList();

        return PageableExecutionUtils.getPage(responseDTOS, pageable, responseDTOS::size);
    }

    private BooleanExpression cursorId(Long cursorId){
        if (cursorId == null) {
            return null;
        }

        return project.projectId.gt(cursorId);
    }
}
