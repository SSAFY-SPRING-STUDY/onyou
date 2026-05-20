package com.ssafy.ssafy_study.member.repository;

import com.ssafy.ssafy_study.member.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {

    private static Map<Long, MemberEntity> repo = new ConcurrentHashMap<>();

    public MemberEntity save(MemberEntity member){
        repo.put(member.getId(),member);
        return member;
    }

    public Optional<MemberEntity> findByUsername(String username){
        for(MemberEntity memberEntity : repo.values() ){
            if(memberEntity.getUsername().equals(username)){
                return Optional.of(memberEntity);
            }
        }
        return null;
    }

    public Optional<MemberEntity> findById(Long memberId){
        if(repo.containsKey(memberId)){
            return Optional.ofNullable(repo.get(memberId));
        }
        else {
            return null;
        }

    }

}
