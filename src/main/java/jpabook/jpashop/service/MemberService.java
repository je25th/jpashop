package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;//javax보다 쓸 수 있는 옵션이 더 많음

import java.util.List;

@Service
@Transactional(readOnly = true)//JPA가 조회하는 곳에서 최적화 해주기 때문에 조회는 readOnly 모드를 쓰자, 쓰기에는 readOnly쓰면 안됨
@RequiredArgsConstructor//final인 애들만 들어가는 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;//final로 해놓는것을 권장
    //생성자 인젝션을 권장함

    /*@Autowired //@RequiredArgsConstructor를 썼기 때문에 필요없음
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     */
    @Transactional//데이터 변경은 꼭 트랜젝션 안에서 이루어져야한다, readOnly=false가 디폴트
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
        //별로 좋은 형태의 코드는 아니다
    }

    //멀티쓰레드 상황을 고려해서 데이터베이스에서 유니크 제약조건을 잡아주든가 해야 안전
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
