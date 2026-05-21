package com.ssafy.ssafy_study.domain.member.service;

import com.ssafy.ssafy_study.domain.member.controller.dto.MemberRequest;
import com.ssafy.ssafy_study.domain.member.controller.dto.MemberResponse;
import com.ssafy.ssafy_study.domain.member.entity.MemberEntity;
import com.ssafy.ssafy_study.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse join(MemberRequest request){
        MemberEntity member = request.toEntity();
        memberRepository.save(member);

        return MemberResponse.from(Optional.of(member));
    }
    public MemberResponse getMemberInfo(Long memberId){
        Optional<MemberEntity> member = memberRepository.findById(memberId);

        return MemberResponse.from(member);
    }
}
