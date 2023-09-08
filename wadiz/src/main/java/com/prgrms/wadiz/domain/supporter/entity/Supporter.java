package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Table(name = "supporters")
@NoArgsConstructor
// @SQLDelete(sql = "UPDATE supporters SET activated = false WHERE supporter_id = ?")  jpa이용핮기!
public class Supporter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supporterId;

    @Column(nullable = false)
    @NotBlank(message = "이름을 입력해주세요.")
    @Min(value = 2, message = "최소 2자 이상입니다.")
    private String supporterName;

    @Column(nullable = false)
    @Email @NotBlank(message = "이메일 형식이 맞지 않습니다.")
    private String supporterEmail;
  
    @Column(nullable = false)
    private boolean activated = Boolean.TRUE; // 활성화 여부 -> 삭제 시 FALSE //// user 상태를 하나 만들기 (정지,탈퇴 등등)

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

    public void changeName(String name) {
        this.supporterName = name;
    }

    public void changeEmail(String email) {
        this.supporterEmail = email;
    }

}
