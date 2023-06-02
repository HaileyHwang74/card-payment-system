package net.e4net.demo;

import groovy.util.logging.Slf4j;
import net.e4net.demo.Entity.Member;
import net.e4net.demo.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.junit.Assert.assertEquals;


@DataJpaTest
@Slf4j
public class AppTest{

    @Autowired MemberRepository memberRepository;
    @Test
    void find() {

        //given
        Optional<Member> member = memberRepository.findByMembId("Test");

        //when
//        five.times(5);

      //boolean
     Boolean result = memberRepository.existsByMembId(member.get().getMembId());


        //then
//        assertEquals(10, five.amount);
        assertEquals(true, result);
    }

}
