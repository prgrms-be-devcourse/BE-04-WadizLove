package com.prgrms.wadiz.domain.project.entity;

import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name="projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
}
