package com.prgrms.wadiz.domain.project.repository;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.PagingDTO;
import com.prgrms.wadiz.domain.project.dto.response.QPagingDTO;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.prgrms.wadiz.domain.funding.QFunding.funding;
import static com.prgrms.wadiz.domain.post.entity.QPost.post;
@Slf4j
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 검색 조건 별 Project 조회
     */
    @Override
    public List<PagingDTO> findAllByCondition(
            String cursorId,
            ProjectSearchCondition searchCondition,
            Pageable pageable
    ) {
        return jpaQueryFactory.select(
                        new QPagingDTO(
                                funding.project.projectId,
                                post.postTitle,
                                post.postThumbNailImage,
                                funding.project.maker.makerBrand,
                                funding.fundingTargetAmount,
                                funding.fundingAmount,
                                funding.project.modifiedAt,
                                funding.fundingEndAt,
                                funding.fundingParticipants
                        ))
                .from(funding)
                .join(post).on(funding.project.projectId.eq(post.project.projectId))
                .where(
                        cursorId(
                                pageable,
                                cursorId
                        ),
                        isDeclined(searchCondition)
                )
                .limit(pageable.getPageSize())
                .orderBy(criterionSort(pageable))
                .fetch();
    }

    /**
     *
     */
    private BooleanExpression isDeclined(ProjectSearchCondition searchCondition) { //TODO
        switch (searchCondition) {
            case OPEN:

                return funding.fundingEndAt.gt(LocalDateTime.now());

            case CLOSE:

                return funding.fundingEndAt.lt(LocalDateTime.now());
        }

        return null;
    }

    /**
     * 조건 별 커서 아이디 커스텀
     */
    private BooleanExpression cursorId(Pageable page, String cursorId) {
        if (cursorId == null) {

            return null;
        }

        StringTemplate stringTemplate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                funding.fundingEndAt
                ,ConstantImpl.create("%Y%m%d%H%i%s")
        );

        StringTemplate stringTemplate2 = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})",
                funding.project.modifiedAt,
                ConstantImpl.create("%Y%m%d%H%i%s")
        );

        for (Sort.Order order : page.getSort()) {
            switch (order.getProperty()) {
                case "fundingAmount":   // 펀딩 금액 순
                    return StringExpressions.lpad(
                            funding.fundingAmount.stringValue(),
                                    12,
                                    '0'
                            )
                            .concat(StringExpressions.lpad(
                                    funding.project.projectId.stringValue(),
                                    8,
                                    '0'
                            ))
                            .lt(cursorId);

                case "fundingEndAt":   // 마감 임박 순
                    return stringTemplate
                            .concat(StringExpressions.lpad(
                                    funding.project.projectId.stringValue(),
                                    8,
                                    '0'
                            ))
                            .lt(cursorId);

                case "modifiedAt": // 최신 순
                    return stringTemplate2
                            .concat(StringExpressions.lpad(
                                    funding.project.projectId.stringValue(),
                                    8,
                                    '0'
                            ))
                            .lt(cursorId);

                default:
                    return StringExpressions.lpad(
                            funding.fundingParticipants.stringValue(),
                                    12,
                                    '0'
                            )
                            .concat(StringExpressions.lpad(
                                    funding.project.projectId.stringValue(),
                                    8,
                                    '0'
                            ))
                            .lt(cursorId);
            }
        }

        return null;
    }

    /**
     * 분류 별 OrderSpecifier
     */
    private OrderSpecifier[] criterionSort(Pageable page) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        for (Sort.Order order : page.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            switch (order.getProperty()) {
                case "fundingAmount":   // 펀딩 금액 순
                    orderSpecifiers.add(new OrderSpecifier(direction, funding.fundingAmount));
                    break;

                case "fundingEndAt":   // 마감 임박 순
                    orderSpecifiers.add(new OrderSpecifier(direction, funding.fundingEndAt));
                    break;

                case "modifiedAt": // 최신 순
                    orderSpecifiers.add(new OrderSpecifier(direction, funding.project.modifiedAt));
                    break;

                default:
                    orderSpecifiers.add(new OrderSpecifier(direction, funding.fundingParticipants));
                    break;
            }
            orderSpecifiers.add(new OrderSpecifier(direction, funding.project.projectId));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
