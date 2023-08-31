package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "supporters")
public class Supporter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supporterId;

    @Column(nullable = false)
    private String supporterName;

    @Column(nullable = false)
    private String supporterEmail;
}
