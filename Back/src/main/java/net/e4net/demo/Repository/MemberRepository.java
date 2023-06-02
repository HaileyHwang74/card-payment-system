package net.e4net.demo.Repository;


import net.e4net.demo.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMembId(String membId);
    boolean existsByMembId(String membId);
}


