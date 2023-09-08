package com.prgrms.wadiz.domain.post.repository;

import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.project.projectId = :projectId")
    Optional<Post> findByProjectId(@Param("projectId") Long projectId);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.project.projectId = :projectId")
    void deleteByProjectId(@Param("projectId") Long projectId);
}
