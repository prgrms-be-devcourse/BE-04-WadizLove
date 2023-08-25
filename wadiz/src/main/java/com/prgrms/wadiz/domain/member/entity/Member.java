package com.prgrms.wadiz.domain.member.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;
}
