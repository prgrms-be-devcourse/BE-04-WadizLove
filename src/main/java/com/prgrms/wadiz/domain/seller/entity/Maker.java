package com.prgrms.wadiz.domain.seller.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "makers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Maker extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maker_id")
    private Long makerId;

    @Column(nullable = false)
    private String makerName;

    @Column(nullable = false)
    private String makerBrand;

    @Column(nullable = false)
    private String makerEmail;
}
