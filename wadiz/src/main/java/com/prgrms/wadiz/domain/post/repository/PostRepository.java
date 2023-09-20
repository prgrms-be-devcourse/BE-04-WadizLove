package com.prgrms.wadiz.domain.post.repository;

import com.prgrms.wadiz.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p FROM Post p WHERE p.project.projectId = :projectId")
    Optional<Post> findByProject_ProjectId(Long projectId); //TODO : 확인 필요

//    @Modifying
//    @Query("DELETE FROM Post p WHERE p.project.projectId = :projectId")
    void deleteByProject_ProjectId(Long projectId);
}
