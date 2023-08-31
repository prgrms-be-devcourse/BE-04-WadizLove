package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.Builder;
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

    @Builder
    public Supporter(Long supporterId, String supporterName, String supporterEmail) {
        this.supporterId = supporterId;
        this.supporterName = supporterName;
        this.supporterEmail = supporterEmail;
    }
}
