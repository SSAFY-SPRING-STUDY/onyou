package com.ssafy.ssafy_study.member.controller;

import com.ssafy.ssafy_study.auth.component.SessionManager;
import com.ssafy.ssafy_study.auth.util.AuthTokenUtils;
import com.ssafy.ssafy_study.member.controller.dto.MemberRequest;
import com.ssafy.ssafy_study.member.controller.dto.MemberResponse;
import com.ssafy.ssafy_study.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public MemberResponse join(@RequestBody MemberRequest request){
        return memberService.join(request);
    }

    @Operation(summary = "내 정보 조회", description = "Authorization Bearer 토큰으로 현재 회원 정보를 조회합니다.")
    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse getMyInfo(
            @Parameter(description = "Bearer access token", required = true, example = "Bearer eyJhbGciOiJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearerToken
    ){
        //bearerToken 을 가지고 세션 키 찾은 다음에 그걸로 id 찾기

        if(AuthTokenUtils.isValidBearerToken(bearerToken)){
           String sessionKey =  AuthTokenUtils.parseBearerToken(bearerToken);
           Long id =  sessionManager.getMemberId(sessionKey);

           return memberService.getMemberInfo(id);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 토큰이 유효하지 않습니다.");    }

}
