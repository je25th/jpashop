package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)//Junit 실행할 때 스프링이랑 엮어서 실행할래!(Junit5에서는 필요없응ㅁ)
@SpringBootTest//스프링 컨테이너 안에서 테스트를 돌릴래!
@Transactional//@Test에서 트랜젝션 어노테이션을 사용하면 기본적으로 롤백함(다른곳에서는 롤백 안함)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(value = false)//롤백 안함
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();//영속성 컨텍스트에 있는 데이터를 DB에 반영
        assertEquals(member, memberRepository.findOne(savedId));//같은 트랜젝션 안에서 pk가 같으면 같은 영속성 컨텍스트로 관리됨
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외 발생

        //then
        fail("예외가 발생해야함");//여기 오면 안됨
    }
}