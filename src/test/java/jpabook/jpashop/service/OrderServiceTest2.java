package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;//Junit4에서 No runnable methods 오류날때 이부분 확인해보기
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest2 {

    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired EntityManager em;
    @Autowired OrderRepository orderRepository;

    @Test
    public void OrderItem_update_쿼리가_나가는지_확인() {
        Member member = new Member();
        memberService.join(member);
        System.out.println("");
        System.out.println("=============member insert=============");
        em.flush();
        System.out.println("==========================");
        System.out.println("");

        Item item = new Book();
        item.setName("item");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemService.saveItem(item);
        System.out.println("");
        System.out.println("=============item insert=============");
        em.flush();
        System.out.println("==========================");
        System.out.println("");

        em.clear();

        System.out.println("");
        System.out.println("=============member select, item select=============");
        Long orderId = orderService.order(member.getId(), item.getId(), 9);
        System.out.println("==========================");

        System.out.println("");
        System.out.println("=============order insert=============");
        System.out.println("=============order.delivery에 CascadeType.ALL이 있기 떄문에 delivery insert 나감=============");
        System.out.println("=============order.orderItem에 CasecadeType.ALL이 있기 때문에 orderItem insert 나감=============");
        System.out.println("=============orderService.order 메소드내에서 item을 영속화 하기때문에 더티체킹하여 item update 나감=============");
        em.flush();
        System.out.println("==========================");
        System.out.println("");

        em.clear();

        System.out.println("");
        System.out.println("=============cancelOrder=============");
        orderService.cancelOrder(orderId);
        System.out.println("==========================");
        System.out.println("");

        System.out.println("");
        System.out.println("=============cancelOrder flush=============");
        em.flush();
        System.out.println("==========================");
        System.out.println("");
    }

    @Test
    public void OrderItem_update_쿼리가_나가는지_확인2() {
        Member member = new Member();
        memberService.join(member);
        em.flush();

        Item item = new Book();
        item.setName("item");
        item.setPrice(10000);
        item.setStockQuantity(10);
        itemService.saveItem(item);
        em.flush();

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        em.clear();

        //주문로직
        OrderItem orderItem = OrderItem.createOrderItem(item, 10000, 2);//item은 준영속상태
        orderRepository.saveOrderItem(orderItem);
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        orderItem.setOrder(order);

        System.out.println("");
        System.out.println("=============주문로직=============");
        System.out.println("=============item이 준영속 상태여서 item update 쿼리 안나감=============");
        em.flush();
        System.out.println("==========================");
        System.out.println("");

        em.clear();

        System.out.println("");
        System.out.println("=============cancelOrder=============");
        System.out.println("=============orderItems에 casecade.ALL을 안했는데 여기서 item이 어떻게 업데이트 되는지 모르겠음, orderItem은 update 안됨=============");
        orderService.cancelOrder(order.getId());
        System.out.println("==========================");
        System.out.println("");

        System.out.println("");
        System.out.println("=============cancelOrder flush=============");
        em.flush();
        System.out.println("==========================");
        System.out.println("");
    }
}
