package com.ssafy.ssafy_study.domain.auth.component;

import com.ssafy.ssafy_study.global.exception.CustomException;
import com.ssafy.ssafy_study.global.exception.error.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    private final Map<String ,Long> sessionStore = new ConcurrentHashMap<>();

    public String createSession(Long memberId){
        //uuid 로 세션키 생성 후 저장
        String sessionKey = UUID.randomUUID().toString();
        sessionStore.put(sessionKey, memberId);
        return sessionKey;
    }

    public void removeSession(String sessionKey){
        //세션 키 삭제
        sessionStore.remove(sessionKey);
    }

    public Long getMemberId(String sessionKey){
        // 세션키로 회원 id 조회 없으면 오류
        Long memberId = sessionStore.get(sessionKey);

        if(memberId == null){
            // 없으면 오류 던지기
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }else {
            return memberId;
        }

    }

}
