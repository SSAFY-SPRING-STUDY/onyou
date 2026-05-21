package com.ssafy.ssafy_study.domain.member.controller;

import com.ssafy.ssafy_study.domain.auth.component.SessionManager;
import com.ssafy.ssafy_study.domain.auth.util.AuthTokenUtils;
import com.ssafy.ssafy_study.domain.member.controller.dto.MemberRequest;
import com.ssafy.ssafy_study.domain.member.controller.dto.MemberResponse;
import com.ssafy.ssafy_study.domain.member.service.MemberService;
import com.ssafy.ssafy_study.global.exception.CustomException;
import com.ssafy.ssafy_study.global.exception.error.ErrorCode;
import com.ssafy.ssafy_study.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequestMapping("api/members")
public class MemberController {
    public final MemberService memberService;
    public final SessionManager sessionManager;

    public MemberController(MemberService memberService, SessionManager sessionManager) {
        this.memberService = memberService;
        this.sessionManager = sessionManager;
    }

    @Operation(summary = "회원가입", description = "새 회원을 등록합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberResponse> join(@RequestBody MemberRequest request){
        MemberResponse response = memberService.join(request);
        return ApiResponse.success("회원가입 성공", response);
    }

    @Operation(summary = "내 정보 조회", description = "Authorization Bearer 토큰으로 현재 회원 정보를 조회합니다.")
    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberResponse> getMyInfo(
            @Parameter(description = "Bearer access token", required = true, example = "Bearer eyJhbGciOiJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearerToken
    ){
        Long id = extractMemberId(bearerToken);
        MemberResponse response = memberService.getMemberInfo(id);
        return ApiResponse.success("내 정보 조회 성공", response);
    }

    private Long extractMemberId(String bearerToken) {
        if (!AuthTokenUtils.isValidBearerToken(bearerToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        String sessionKey = AuthTokenUtils.parseBearerToken(bearerToken);
        return sessionManager.getMemberId(sessionKey);
    }

}
