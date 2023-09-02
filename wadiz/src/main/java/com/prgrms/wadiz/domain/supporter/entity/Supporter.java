package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.global.BaseEntity;

import lombok.Builder;
import lombok.Getter;

import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "supporters")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE supporters SET activated = false WHERE supporter_id = ?")
public class Supporter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supporterId;

    @Column(nullable = false)
    private String supporterName;

    @Column(nullable = false)
    private String supporterEmail;
  
    @Column(nullable = false)
    private boolean activated = Boolean.TRUE; // 활성화 여부 -> 삭제 시 FALSE

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

    public static SupporterCreateRequestDTO toDTOForRequest(Supporter supporter) {
        return new SupporterCreateRequestDTO(supporter.getSupporterName(), supporter.getSupporterEmail());
    }

    public static SupporterResponseDTO toDTOForResponse(Supporter supporter) {
        return new SupporterResponseDTO(supporter.getSupporterName(), supporter.getSupporterEmail());
    }

}
