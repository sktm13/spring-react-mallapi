package org.yujin.mallapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.yujin.mallapi.domain.Member;
import org.yujin.mallapi.domain.MemberRole;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 데이터 주입
    @Test
    public void testInsertMember() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .pw(passwordEncoder.encode("1111"))
                    .nickname("USER" + i)
                    .build();

            member.addRole(MemberRole.USER);

            if (i >= 5) {
                member.addRole(MemberRole.MANAGER);
            }

            if (i >= 8) {
                member.addRole(MemberRole.ADMIN);
            }

            memberRepository.save(member);
        }
    }

    // admin@aaa.com에 ADMIN권한 주입
    @Test
    public void testInsertAdmin() {
        Member member = Member.builder()
                .email("admin@aaa.com")
                .pw(passwordEncoder.encode("1111"))
                .nickname("ADMIN")
                .build();
        member.addRole(MemberRole.USER);
        member.addRole(MemberRole.MANAGER);
        member.addRole(MemberRole.ADMIN);

        memberRepository.save(member);
    }

    @Test
    public void testInsertUser1() {
        Member member = Member.builder()
                .email("user1@aaa.com")
                .pw(passwordEncoder.encode("1111"))
                .nickname("user1")
                .build();
        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }

    @Test
    public void testRead() {

        String email = "user9@aaa.com";

        Member member = memberRepository.getWithRoles(email);

        log.info("-----------------");
        log.info(member);
        log.info(member.getMemberRoleList());
    }

}
