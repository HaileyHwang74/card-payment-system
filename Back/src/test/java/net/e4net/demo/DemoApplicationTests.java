package net.e4net.demo;


import net.e4net.demo.Entity.Member;
import net.e4net.demo.Repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.junit.Assert.assertEquals;

//@Slf4j
//@SpringBootTest
@DataJpaTest
@DisplayName("member 일치여부 확인")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemoApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findMember() {

        //given
        Optional<Member> member = memberRepository.findByMembId("test");

        //when
        Boolean result = memberRepository.existsByMembId(member.get().getMembId());


        //then
        assertEquals(true, result);
    }
}



