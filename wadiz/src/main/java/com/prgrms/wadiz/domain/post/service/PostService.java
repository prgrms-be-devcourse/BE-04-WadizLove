package com.prgrms.wadiz.domain.post.service;

import com.prgrms.wadiz.domain.post.dto.request.PostCreateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.request.PostUpdateRequestDTO;
import com.prgrms.wadiz.domain.post.dto.response.PostResponseDTO;
import com.prgrms.wadiz.domain.post.entity.Post;
import com.prgrms.wadiz.domain.post.repository.PostRepository;
import com.prgrms.wadiz.domain.project.ProjectStatus;
import com.prgrms.wadiz.domain.project.dto.ProjectServiceDTO;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.global.util.exception.BaseException;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * Post 생성
     */
    @Transactional
    public Long createPost(
            Long projectId,
            ProjectServiceDTO projectServiceDTO,
            PostCreateRequestDTO postCreateRequestDTO
    ) {
        if(postRepository.existsByProject_ProjectId(projectId)){
            log.warn("cannot create post");

            throw new BaseException(ErrorCode.CANNOT_CREATE_POST);
        }

        Project project = ProjectServiceDTO.toEntity(projectServiceDTO);

        Post post = Post.builder()
                .project(project)
                .postTitle(postCreateRequestDTO.postTitle())
                .postDescription(postCreateRequestDTO.postDescription())
                .postThumbNailImage(postCreateRequestDTO.postThumbNailImage())
                .postContentImage(postCreateRequestDTO.postContentImage())
                .build();

        Post savedPost = postRepository.save(post);

        return savedPost.getPostId();
    }

    /**
     * Post 조회
     */
    @Transactional(readOnly = true)
    public PostResponseDTO getPostByProjectId(Long projectId) {
        Post post = postRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Post for Project {} is not found", projectId);

                    throw new BaseException(ErrorCode.POST_NOT_FOUND);
                });

        return PostResponseDTO.from(post);
    }

    /**
     * Post 정보 수정
     */
    @Transactional
    public void updatePost(
            Long projectId,
            PostUpdateRequestDTO postUpdateRequestDTO
    ) {
        Post post = postRepository.findByProject_ProjectId(projectId)
                .orElseThrow(() -> {
                    log.warn("Post for Project {} is not found", projectId);

                    throw new BaseException(ErrorCode.POST_NOT_FOUND);
                });

        if (!isProjectBeforeSetUp(post.getProject())) {
            throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
        }

        post.updatePost(
                postUpdateRequestDTO.postTitle(),
                postUpdateRequestDTO.postDescription(),
                postUpdateRequestDTO.postThumbNailImage(),
                postUpdateRequestDTO.postContentImage()
        );
    }

    /**
     * Post 삭제
     */
    @Transactional
    public void deletePost(Long projectId) {
        Optional<Post> post = postRepository.findByProject_ProjectId(projectId);
        if(post.isPresent()){
            if (!isProjectBeforeSetUp(post.get().getProject())) {
                log.warn("project is already launched");

                throw new BaseException(ErrorCode.PROJECT_ACCESS_DENY);
            }

            postRepository.deleteByProject_ProjectId(projectId);
        }
    }

    /**
     * Post 존재 여부
     */
    public boolean isPostExist(Long projectId) {
        if(postRepository.existsByProject_ProjectId(projectId)){
            return true;
        }
        log.warn("Post for Project {} is not found", projectId);

        throw new BaseException(ErrorCode.POST_NOT_FOUND);
    }

    /**
     * Project가 개설된 상태인지 확인
     */
    private boolean isProjectBeforeSetUp(Project project) {
        return project.getProjectStatus() == ProjectStatus.READY;
    }
}
