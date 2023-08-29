package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "supporters")
public class Supporter extends BaseEntity {
    @Id
    @Column(name="supporter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
