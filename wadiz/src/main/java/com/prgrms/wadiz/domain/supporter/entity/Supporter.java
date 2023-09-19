package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.BaseEntity;

import com.prgrms.wadiz.domain.supporter.SupporterStatus;
import com.prgrms.wadiz.global.annotation.ValidEnum;
import lombok.Builder;
import lombok.Getter;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Getter
@Table(name = "supporters")
@NoArgsConstructor
public class Supporter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supporterId;

    @Column(nullable = false)
    @Size(min = 2, message = "최소 2자 이상입니다.")
    private String supporterName;

    @Column(nullable = false)
    @Email(message = "이메일 정보를 입력해 주세요")
    private String supporterEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = SupporterStatus.class, message = "해당하는 서포터 상태가 존재하지 않습니다.")
    private SupporterStatus status = SupporterStatus.REGISTERED;

    @Builder
    public Supporter(
            String supporterName,
            String supporterEmail
    ) {
        this.supporterName = supporterName;
        this.supporterEmail = supporterEmail;
    }

    @Builder
    public Supporter(
            String name,
            String email,
            SupporterStatus supporterStatus
    ) {
        this.supporterName = name;
        this.supporterEmail = email;
        this.status = supporterStatus;
    }

    public void updateSupporter(String supporterName, String supporterEmail) {
        this.supporterName = supporterName;
        this.supporterEmail = supporterEmail;
    }

    public void unregisteredSupporter(){
        this.status = SupporterStatus.UNREGISTERED;
    }
}
