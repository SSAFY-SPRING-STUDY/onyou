package com.ssafy.ssafy_study.auth.component;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"유효하지 않음");
        }else {
            return memberId;
        }

    }

}
