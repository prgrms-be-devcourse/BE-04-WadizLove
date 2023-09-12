package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.prgrms.wadiz.domain.project.entity.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Project> findAllByCondition(
            Long cursorId,
            ProjectSearchCondition projectSearchCondition,
            Pageable pageable
    ) {
        List<Project> findBoards = jpaQueryFactory.selectFrom(project)
                .where(cursorId(cursorId))
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifier(pageable.getSort())
                        .stream()
                        .toArray(OrderSpecifier[]::new))
                .fetch();

        return findBoards;
    }
    
    private List<OrderSpecifier> getOrderSpecifier(Sort sort){
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Project.class, "project");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        return orders;
    }

    private BooleanExpression cursorId(Long cursorId){
        if (cursorId == null) {
            return null;
        }

        return project.projectId.gt(cursorId);
    }
}
