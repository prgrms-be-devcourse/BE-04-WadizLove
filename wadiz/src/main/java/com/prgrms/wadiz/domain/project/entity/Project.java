package com.prgrms.wadiz.domain.project.entity;

import com.prgrms.wadiz.domain.maker.entity.Maker;
import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @OneToOne
    @JoinColumn(name = "maker_id")
    private Maker maker;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Builder
    public Project(Maker maker) {
        this.maker = maker;
        this.projectStatus = ProjectStatus.READY;
    }

    public void setUpProject() {
        this.projectStatus = ProjectStatus.SET_UP;
    }
}
