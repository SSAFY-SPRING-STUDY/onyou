package com.ssafy.ssafy_study.domain.member.repository;

import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
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
        return repo.values().stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }

    public Optional<MemberEntity> findById(Long memberId){
        return Optional.ofNullable(repo.get(memberId));
    }

}
