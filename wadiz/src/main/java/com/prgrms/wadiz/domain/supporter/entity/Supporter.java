package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.supporter.SupporterStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @Size(
            min = 2,
            message = "최소 2자 이상입니다."
    )
    @Pattern(
            regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$",
            message = "올바른 이름 형식이 아닙니다."
    )
    @NotBlank(message = "서포터 이름을 입력해주세요")
    private String supporterName;

    @Column(nullable = false)
    @NotBlank(message = "이메일 정보는 빈칸이 될 수 없습니다.")
    @Email(message = "이메일 정보를 입력해 주세요")
    private String supporterEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
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

    public void updateSupporter(
            String supporterName,
            String supporterEmail
    ) {
        this.supporterName = supporterName;
        this.supporterEmail = supporterEmail;
    }

    public void unregisteredSupporter(){
        this.status = SupporterStatus.UNREGISTERED;
    }
}
