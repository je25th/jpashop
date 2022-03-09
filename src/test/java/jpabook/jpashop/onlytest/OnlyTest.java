package jpabook.jpashop.onlytest;

import jpabook.jpashop.onlytest.all.InsideEntityAll;
import jpabook.jpashop.onlytest.all.ManyEntityAll;
import jpabook.jpashop.onlytest.notall.InsideEntity;
import jpabook.jpashop.onlytest.notall.ManyEntity;
import jpabook.jpashop.onlytest.notall.OneEntity;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OnlyTest {
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("FK를 안 가진 엔티티를 persist 하지 않고 연관관계 주인(FK가진 애)에 set 한 다음 연관관계 주인을 persist -> 에러남!")
    public void test1() {
        InsideEntity insideEntity = new InsideEntity();
        insideEntity.setName("이름");
        //em.persist(insideEntity);//먼저 insideEntity를 persist하지 않으면 에러남!

        ManyEntity manyEntity = new ManyEntity();
        manyEntity.setInsideEntity(insideEntity);
        em.persist(manyEntity);

        System.out.println("\n==============insideEntity, manyEntity insert==============");
        em.flush();
        System.out.println("============================\n");
    }

    @Test
    @DisplayName("FK를 안 가진 엔티티(casecade.ALL설정)를 persist 하지 않고 연관관계 주인(FK가진 애)에 set 한 다음 연관관계 주인을 persist -> 에러 안남!")
    public void test1_All() {
        InsideEntityAll insideEntityAll = new InsideEntityAll();
        insideEntityAll.setName("이름");
        em.persist(insideEntityAll);

        ManyEntityAll manyEntityAll = new ManyEntityAll();
        manyEntityAll.setInsideEntity(insideEntityAll);
        em.persist(manyEntityAll);

        System.out.println("\n==============insideEntity, manyEntity insert==============");
        em.flush();
        System.out.println("============================\n");
    }

    @Test
    @DisplayName("연관관계 주인을 통해 하위(?) 엔티티의 데이터를 바꿀 경우 더티체킹 되어 update 쿼리 나감")
    public void test2() {
        InsideEntity insideEntity = new InsideEntity();
        insideEntity.setName("이름");
        em.persist(insideEntity);//먼저 insideEntity를 persist하지 않으면 에러남!

        ManyEntity manyEntity = new ManyEntity();
        manyEntity.setInsideEntity(insideEntity);
        em.persist(manyEntity);

        em.flush();
        em.clear();

        ManyEntity findManyEntity = em.find(ManyEntity.class, manyEntity.getId());
        findManyEntity.getInsideEntity().setName("이름 수정");

        System.out.println("\n==============insideEntity update==============");
        em.flush();
        System.out.println("============================\n");
    }

    @Test
    @DisplayName("연관관계 거울을 통해 하위(?)엔티티에 접근하여 또 그 하위(??)엔티티의 데이터를 바꾸면 무슨일이 벌어짐? -> update 나감!!!")
    public void test3() {
        InsideEntity insideEntity = new InsideEntity();
        insideEntity.setName("이름");
        em.persist(insideEntity);//먼저 insideEntity를 persist하지 않으면 에러남!

        ManyEntity manyEntity = new ManyEntity();
        manyEntity.setInsideEntity(insideEntity);
        em.persist(manyEntity);

        OneEntity oneEntity = new OneEntity();
        em.persist(oneEntity);

        manyEntity.setOneEntity(oneEntity);

        em.flush();
        em.clear();

        //첫번째 경우
        OneEntity findOne = em.find(OneEntity.class, oneEntity.getId());
        List<ManyEntity> manyEntities = findOne.getManyEntities();
        for (ManyEntity manyEn : manyEntities) {
            manyEn.getInsideEntity().setName("이름수정");
            manyEn.setText("수정!!!");
        }

        System.out.println("\n==============1) insideEntity update?? -> ㅇㅇ!!==============");
        System.out.println("==============manyEntity update?? -> ㅇㅇ!!==============");
        em.flush();
        System.out.println("============================\n");

        em.clear();

        //두번째 경우
        OneEntity findOne2 = em.find(OneEntity.class, oneEntity.getId());
        findOne2.getManyEntities().get(0).setText("수정2");

        System.out.println("\n==============2) manyEntity update?? -> ㅇㅇ!!==============");
        em.flush();
        System.out.println("============================\n");
    }

    /**
     * 결론 지금까지 착각하고있었는데, mappedBy 설정 했을때 연관관계 거울이라는게 거기서부터 접근하면 모든게 수정안된다는 소리가 아니라
     * list.add(nn); 한것에 대한 변경 감지가 안된다는 소리인가보다. list.add(nn)해도 nn의 FK가 업데이트 되지 않는다! 라는! 뜻?!
     * 그니까 FK 업데이트를 감지하는게 연관관계 주인에서만 가능하다~ 라는 소리구나!
     * 그니까2 트랜젝션 안에서 select(조회)된 엔티티는 영속상태가 되는구나.....?
     */

}
