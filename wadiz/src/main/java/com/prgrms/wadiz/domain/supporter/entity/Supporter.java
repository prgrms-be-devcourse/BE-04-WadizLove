package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    private boolean activated = Boolean.TRUE;
    // 활성화 여부 -> 삭제 시 FALSE //// user 상태를 하나 만들기 (정지,탈퇴 등등)

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
            Boolean isActivated
    ) {
        this.supporterName = name;
        this.supporterEmail = email;
        this.activated = isActivated;
    }

    public void updateSupporter(String supporterName, String supporterEmail) {
        this.supporterName = supporterName;
        this.supporterEmail = supporterEmail;
    }

    public void deActivateSupporter(){
        this.activated = Boolean.FALSE;
    }
}
