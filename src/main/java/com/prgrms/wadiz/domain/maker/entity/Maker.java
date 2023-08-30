package com.prgrms.wadiz.domain.maker.entity;

import com.prgrms.wadiz.domain.maker.dto.request.MakerCreateRequestDTO;
import com.prgrms.wadiz.domain.maker.dto.response.MakerResponseDTO;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "makers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE makers SET deleted = true WHERE maker_id = ?")
public class Maker extends BaseEntity{

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

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE; // 삭제 여부 기본값 false

    @Builder
    public Maker(Long makerId, String makerName, String makerBrand, String makerEmail) {
        this.makerId = makerId;
        this.makerName = makerName;
        this.makerBrand = makerBrand;
        this.makerEmail = makerEmail;
    }

    @Builder
    public Maker(String makerName, String makerBrand, String makerEmail) {
        this.makerName = makerName;
        this.makerBrand = makerBrand;
        this.makerEmail = makerEmail;
    }

    public static MakerCreateRequestDTO toDTOForRequest(Maker maker) {
        return new MakerCreateRequestDTO(maker.makerName, maker.makerBrand, maker.makerEmail);
    }
    public static MakerResponseDTO toDTOForResponse(Maker maker) {
        return new MakerResponseDTO(maker.makerId, maker.makerName, maker.makerBrand, maker.makerEmail);
    }
}
