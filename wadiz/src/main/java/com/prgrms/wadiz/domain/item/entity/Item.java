package com.prgrms.wadiz.domain.item.entity;

import com.prgrms.wadiz.domain.funding.entity.Funding;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "items")
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id")
    private Funding funding;

    @Column(nullable = false)
    private String name;

}
