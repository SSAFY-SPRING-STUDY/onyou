package com.ssafy.ssafy_study.auth.service;

import com.ssafy.ssafy_study.auth.component.SessionManager;
import com.ssafy.ssafy_study.auth.controller.dto.LoginRequest;
import com.ssafy.ssafy_study.auth.controller.dto.LoginResponse;
import com.ssafy.ssafy_study.member.controller.dto.MemberResponse;
import com.ssafy.ssafy_study.member.entity.MemberEntity;
import com.ssafy.ssafy_study.member.repository.MemberRepository;
import com.ssafy.ssafy_study.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final SessionManager sessionManager;
    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest request) {
        //username 조회 -> 비밀번호 검증 -> 세션 생성
        Optional<MemberEntity> member  = memberRepository.findByUsername(request.username());

        if(member == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        if (!MemberEntity.checkPassword(request.password())) {
            // 에러 던지기
        }
        String sessionKey = sessionManager.createSession(member.get().getId()) ;
        return LoginResponse.from(sessionKey);
    }
    public void logout(String sessionKey){
        sessionManager.removeSession(sessionKey);
    }
}
