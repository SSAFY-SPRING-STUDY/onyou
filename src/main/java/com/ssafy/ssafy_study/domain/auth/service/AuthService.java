package com.ssafy.ssafy_study.domain.auth.service;

import com.ssafy.ssafy_study.domain.auth.component.SessionManager;
import com.ssafy.ssafy_study.domain.auth.controller.dto.LoginRequest;
import com.ssafy.ssafy_study.domain.auth.controller.dto.LoginResponse;
import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import com.ssafy.ssafy_study.domain.member.repository.MemberRepository;
import com.ssafy.ssafy_study.global.exception.CustomException;
import com.ssafy.ssafy_study.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final SessionManager sessionManager;
    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest request) {
        //username 조회 -> 비밀번호 검증 -> 세션 생성
        MemberEntity member = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_USERNAME));

        if (!member.checkPassword(request.password())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        
        String sessionKey = sessionManager.createSession(member.getId()) ;
        return LoginResponse.from(sessionKey);
    }
    public void logout(String sessionKey){
        sessionManager.removeSession(sessionKey);
    }
}
