package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import lombok.*;

import javax.persistence.*;

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
    private String supporterName;

    @Column(nullable = false)
    private String supporterEmail;
  
    @Column(nullable = false)
    private boolean activated = Boolean.TRUE; // 활성화 여부 -> 삭제 시 FALSE //// user 상태를 하나 만들기 (정지,탈퇴 등등)

    @Builder
    public Supporter(
            String name,
            String email
    ) {
        this.supporterName = name;
        this.supporterEmail = email;
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
