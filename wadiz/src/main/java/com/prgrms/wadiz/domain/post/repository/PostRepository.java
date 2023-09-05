package com.prgrms.wadiz.domain.post.repository;

import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByProjectId(Long projectId);
}
