package com.prgrms.wadiz.domain.supporter.entity;

import com.prgrms.wadiz.domain.supporter.dto.request.SupporterCreateRequestDTO;
import com.prgrms.wadiz.domain.supporter.dto.response.SupporterResponseDTO;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "supporters")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE supporters SET deleted = true WHERE supporter_id = ?")
public class Supporter extends BaseEntity {
    @Id
    @Column(name="supporter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE; // 삭제 여부 기본값 false

    @Builder
    public Supporter(
            String name,
            String email
    ) {
        this.name = name;
        this.email = email;
    }

    @Builder
    public Supporter(
            String name,
            String email,
            Boolean isDeleted
    ) {
        this.name = name;
        this.email = email;
        this.deleted = isDeleted;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public static SupporterCreateRequestDTO toDTOForRequest(Supporter supporter) {
        return new SupporterCreateRequestDTO(supporter.getName(), supporter.getEmail());
    }

    public static SupporterResponseDTO toDTOForResponse(Supporter supporter) {
        return new SupporterResponseDTO(supporter.getName(), supporter.getEmail());
    }

}