package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
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
    public Page<Project> findAllByCondition(
            Long cursorId,
            ProjectSearchCondition projectSearchCondition,
            Pageable pageable
    ) {
        List<Project> findBoards = jpaQueryFactory.selectFrom(project)
                .where(cursorId(cursorId))
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(
                findBoards,
                pageable,
                findBoards::size
        );
    }

    private BooleanExpression cursorId(Long cursorId){
        if (cursorId == null) {
            return project.projectId.gt(0L);
        }

        return project.projectId.gt(cursorId);
    }
}
