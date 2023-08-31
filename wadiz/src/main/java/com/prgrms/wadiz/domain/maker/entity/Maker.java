package com.prgrms.wadiz.domain.maker.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "makers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Maker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maker_id")
    private Long makerId;

    @Column(nullable = false)
    private String makerName;

    @Column(nullable = false)
    private String makerBrand;

    @Column(nullable = false)
    private String makerEmail;

    @Builder
    public Maker(
            String makerName,
            String makerBrand,
            String makerEmail
    ) {
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
