package com.prgrms.wadiz.domain.maker.entity;

import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.domain.maker.MakerStatus;
import com.prgrms.wadiz.global.annotation.ValidEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Size(min = 2, message = "이름은 최소 2자 이상입니다.")
    private String makerName;

    @Column(nullable = false)
    @NotBlank(message = "브랜드를 입력해주세요.")
    private String makerBrand;

    @Column(nullable = false)
    @Email(message = "이메일을 입력해주세요.")
    private String makerEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = MakerStatus.class, message = "해당하는 메이커 상태가 존재하지 않습니다.")
    private MakerStatus status = MakerStatus.REGISTERED;

    @Builder
    public Maker(
            Long makerId,
            String makerName,
            String makerBrand,
            String makerEmail
    ) {
        this.makerId = makerId;
        this.makerName = makerName;
        this.makerBrand = makerBrand;
        this.makerEmail = makerEmail;
    }

    public void updateMaker(
            String makerName,
            String makerEmail,
            String makerBrand
    ){
        this.makerName = makerName;
        this.makerBrand = makerBrand;
        this.makerEmail = makerEmail;
    }

    public void unregisteredMaker() {
        this.status = MakerStatus.UNREGISTERED;
    }

}