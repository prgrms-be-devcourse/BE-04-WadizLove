package com.prgrms.wadiz.domain.project.controller;

import com.prgrms.wadiz.domain.project.condition.ProjectSearchCondition;
import com.prgrms.wadiz.domain.project.dto.response.ProjectResponseDTO;
import com.prgrms.wadiz.domain.project.dto.response.ProjectSummaryResponseDTO;
import com.prgrms.wadiz.domain.project.service.ProjectUseCase;
import com.prgrms.wadiz.global.annotation.ApiErrorCodeExample;
import com.prgrms.wadiz.global.util.exception.ErrorCode;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "projects", description = "프로젝트 API")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectUseCase projectUseCase;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "프로젝트 개설 성공"),
            @ApiResponse(responseCode = "404",
                    description = "프로젝트 개설 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Project")
    @Operation(summary = "프로젝트 개설 시작", description = "매이커의 id를 입력받아 프로젝트 개설을 시작한다.")
    @PostMapping("/maker/{makerId}")
    public ResponseEntity<ResponseTemplate> startProject(@Parameter(description = "메이커 id")@PathVariable Long makerId) {
        ProjectResponseDTO projectResponseDTO = projectUseCase.startProject(makerId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "프로젝트 생성 성공"),
            @ApiResponse(responseCode = "404",
                    description = "프로젝트 생성 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Project")
    @Operation(summary = "프로젝트 생성", description = "프로젝트와 매이커의 id를 받아 프로젝트를 생성한다.")
    @PostMapping("/{projectId}/maker/{makerId}/launching")
    public ResponseEntity<ResponseTemplate> createProject(
            @Parameter(description = "프로젝트 id") @PathVariable Long projectId,
            @Parameter(description = "메이커 id") @PathVariable Long makerId
    ) {
        projectUseCase.createProject(projectId, makerId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "프로젝트 조회 성공"),
            @ApiResponse(responseCode = "404",
                    description = "프로젝트 조회 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Project")
    @Operation(summary = "프로젝트 조회", description = "프로젝트의 id로 프로젝트를 조회한다.")
    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseTemplate> getProject(@Parameter(description = "프로젝트 id")@PathVariable Long projectId) {
        ProjectResponseDTO projectResponseDTO = projectUseCase.getProject(projectId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectResponseDTO));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "프로젝트 목록 조회 성공"),
            @ApiResponse(responseCode = "404",
                    description = "프로젝트 목록 조회 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Project")
    @Operation(summary = "프로젝트 목록 조회", description = "커서 id, 페이지 size, 검색 조건, 분류를 받아 프로젝트 목록을 조회한다.")
    @GetMapping
    public ResponseEntity<ResponseTemplate> getProjects(
            @Parameter(description = "커서 id")
            @RequestParam(required = false) String cursorId,
            @Parameter(description = "페이지 size")
            @RequestParam int size,
            @Parameter(description = "검색 조건")
            @RequestParam ProjectSearchCondition searchCondition,
            @Parameter(description = "분류")
            @RequestParam(required = false) String criterion
            ) {
        ProjectSummaryResponseDTO projectSummaryRes = projectUseCase.getProjects(
                cursorId,
                size,
                searchCondition,
                criterion
        );

        return ResponseEntity.ok(ResponseFactory.getSingleResult(projectSummaryRes));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "프로젝트 목록 조회 성공"),
            @ApiResponse(responseCode = "404",
                    description = "프로젝트 목록 조회 실패")
    })
    @ApiErrorCodeExample(value = ErrorCode.class, domain = "Project")
    @Operation(summary = "프로젝트 런칭 전 삭제", description = "프로젝트 id를 받아 프로젝트와 연결된 (funding,post,reward를 모두 삭제한다.")
    @DeleteMapping("{projectId}")
    public ResponseEntity<ResponseTemplate> deleteProjectBeforeLaunching(
            @PathVariable Long projectId
    ){
        projectUseCase.deleteProject(projectId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

}
