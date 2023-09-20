package com.prgrms.wadiz.domain.project.entity;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maker_id")
    private Maker maker;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank(message = "프로젝트 상태를 입력해주세요.")
    private ProjectStatus projectStatus;

    @Builder
    public Project(Long projectId, Maker maker) {
        this.projectId = projectId;
        this.maker = maker;
        this.projectStatus = ProjectStatus.READY;
    }

    public void setUpProject() {
        this.projectStatus = ProjectStatus.SET_UP;
    }
}
