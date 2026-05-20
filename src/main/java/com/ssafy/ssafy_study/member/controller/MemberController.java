package com.ssafy.ssafy_study.member.controller;

import com.ssafy.ssafy_study.auth.component.SessionManager;
import com.ssafy.ssafy_study.auth.util.AuthTokenUtils;
import com.ssafy.ssafy_study.member.controller.dto.MemberRequest;
import com.ssafy.ssafy_study.member.controller.dto.MemberResponse;
import com.ssafy.ssafy_study.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/members")
public class MemberController {
    public final MemberService memberService;
    public final SessionManager sessionManager;

    public MemberController(MemberService memberService, SessionManager sessionManager) {
        this.memberService = memberService;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse join(@RequestBody MemberRequest request){
        return memberService.join(request);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public MemberResponse getMyInfo(@RequestHeader("Authorization") String bearerToken){
        //bearerToken 을 가지고 세션 키 찾은 다음에 그걸로 id 찾기

        if(AuthTokenUtils.isValidBearerToken(bearerToken)){
           String sessionKey =  AuthTokenUtils.parseBearerToken(bearerToken);
           Long id =  sessionManager.getMemberId(sessionKey);

           return memberService.getMemberInfo(id);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 토큰이 유효하지 않습니다.");    }

}
