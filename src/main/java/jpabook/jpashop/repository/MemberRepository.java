package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext//원래는 @PersistenceContext를 써야하지만, spring boot의 spring data jpa를 쓰면 @Autowired를 써도 됨!
    private final EntityManager em;//@PersistenceContext : JPA 표준 어노테이션, 스프링이 EntityManager를 주입해줌

    /**
     * 스프링 프레임워크는 여기에 실제 EntityManager를 주입하는 것이 아니라,
     * 사실은 실제 EntityManager를 연결해주는 가짜 EntityManager를 주입해둡니다.
     * 그리고 이 EntityManager를 호출하면, 현재 데이터베이스 트랜잭션과 관련된 실제 EntityManager를 호출해줍니다.
     * --> 그러니까 Service 단에서 여러 Repository를 사용해도
     *     한 트랜잭션 안 이라면 같은(트랜잭션 어노테이션이 선언된 메서드 안 이라면) 영속성 컨텍스트를 사용함
     */

    /**
     * 엔티티 매니저 팩토리를 주입받고 싶을 때
     * @PersistenceUnit
     * private EntityManagerFactory emf;
     */

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
