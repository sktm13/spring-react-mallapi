package org.yujin.mallapi.security;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yujin.mallapi.domain.Member;
import org.yujin.mallapi.dto.MemberDTO;
import org.yujin.mallapi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override                               // id = email //
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("----------loadUserByUsername----------" + username);

        Member member = memberRepository.getWithRoles(username);

        if (member == null) {
            throw new UsernameNotFoundException("사용자 없음");
        }

        MemberDTO memberDTO = new MemberDTO(member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream()
                        .map(memberRole -> memberRole.name()).collect(Collectors.toList()));

        log.info(memberDTO);

        return memberDTO;
    }

}
