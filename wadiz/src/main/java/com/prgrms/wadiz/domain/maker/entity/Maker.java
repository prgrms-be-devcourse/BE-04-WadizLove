package com.prgrms.wadiz.domain.maker.entity;

import com.prgrms.wadiz.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Table(name = "makers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE makers SET activated = false WHERE maker_id = ?")
public class Maker extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maker_id")
    private Long makerId;

    @Column(nullable = false)
    @Min(value = 2, message = "이름은 최소 2자 이상입니다.")
    private String makerName;

    @Column(nullable = false)
    @NotBlank(message = "이름을 입력해주세요.")
    private String makerBrand;

    @Column(nullable = false)
    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String makerEmail;

    @Column(nullable = false)
    private boolean activated = Boolean.TRUE; // 활성화 여부 -> 삭제 시 FALSE

    @Builder
    public Maker(
            String makerName,
            String makerBrand,
            String makerEmail) {

        this.makerName = makerName;
        this.makerBrand = makerBrand;
        this.makerEmail = makerEmail;

    }
    public void changeMakerName(String makerName) {
        this.makerName = makerName;
    }

    public void chaneMakerBrand(String makerBrand) {
        this.makerBrand = makerBrand;
    }

    public void changeMakerEmail(String makerEmail) {
        this.makerEmail = makerEmail;
    }

}