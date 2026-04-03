package org.yujin.mallapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yujin.mallapi.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
    
    @EntityGraph(attributePaths = "memberRoleList")
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Member getWithRoles(@Param("email") String email);
    
}
