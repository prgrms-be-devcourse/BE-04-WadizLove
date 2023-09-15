package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.domain.post.entity.QPost;
import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.querydsl.core.Tuple;
import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.prgrms.wadiz.domain.project.entity.QProject.project;
import static com.prgrms.wadiz.domain.post.entity.QPost.post;
import static com.prgrms.wadiz.domain.funding.entity.QFunding.funding;
import static com.prgrms.wadiz.domain.maker.entity.QMaker.maker;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Project> findAllByCondition(
            Long cursorId,
            ProjectSearchCondition searchCondition,
            Pageable pageable
    ) {
        List<Funding> findBoards = jpaQueryFactory
                .selectFrom(funding)
                .where(
                        cursorId(cursorId),
                         isDeclined(searchCondition)
                )
                .limit(pageable.getPageSize())
                .orderBy(criterionSort(pageable))
                .fetch();

        List<Project> projects = findBoards.stream().map(
                funding1 -> funding1.getProject()
        ).toList();

        return projects;
    }

    private BooleanExpression isDeclined(ProjectSearchCondition searchCondition) {
        switch (searchCondition){
            case OPEN:
                return funding.fundingEndAt.gt(LocalDateTime.now());
            case CLOSE:
                return funding.fundingEndAt.lt(LocalDateTime.now());
            default:
                return null;
        }
    }

    private OrderSpecifier<?> criterionSort(Pageable page) {
        if (!page.getSort().isEmpty()) {
            for (Sort.Order order : page.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "fundingAmount":   // 펀딩 금액 순
                        return new OrderSpecifier(direction, funding.fundingAmount);
                    case "fundingEndAt":   // 마감 임박 순
                        return new OrderSpecifier(direction, funding.fundingEndAt);
                    case "createdAt": // 최신 순
                        return new OrderSpecifier(direction, project.createdAt);
                }
            }
        }

        return null;
    }

    private BooleanExpression cursorId(Long cursorId){
        if (cursorId == null) {

            return null;
        }

        return project.projectId.gt(cursorId);
    }
}
