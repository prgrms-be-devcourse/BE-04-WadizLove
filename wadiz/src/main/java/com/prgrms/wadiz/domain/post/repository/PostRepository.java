package com.prgrms.wadiz.domain.post.repository;

import com.prgrms.wadiz.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
