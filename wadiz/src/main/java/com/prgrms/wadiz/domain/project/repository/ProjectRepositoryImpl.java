package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.PagingDTO;
import com.prgrms.wadiz.domain.project.dto.response.QExampleClass;
import com.prgrms.wadiz.domain.project.dto.response.QPagingDTO;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static com.prgrms.wadiz.domain.project.entity.QProject.project;
import static com.prgrms.wadiz.domain.funding.entity.QFunding.funding;
import static com.prgrms.wadiz.domain.post.entity.QPost.post;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PagingDTO> findAllByCondition(
            Long cursorId,
            ProjectSearchCondition searchCondition,
            Pageable pageable
    ) {
        return  jpaQueryFactory.select(
                new QPagingDTO(
                        funding.project.projectId,
                        post.postTitle,
                        post.postThumbNailImage,
                        funding.project.maker.makerBrand,
                        funding.fundingTargetAmount,
                        funding.fundingAmount
                ))
                .from(funding)
                .join(post).on(funding.project.projectId.eq(post.project.projectId))
                .where(
                        cursorId(cursorId),
                        isDeclined(searchCondition)
                )
                .limit(pageable.getPageSize())
                .orderBy(criterionSort(pageable))
                .fetch();
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

        return funding.project.projectId.gt(cursorId);
    }
}
