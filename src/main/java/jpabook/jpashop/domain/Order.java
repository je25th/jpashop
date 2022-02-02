package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne//연관관계주인
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")//거울
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne//연관관계주인
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간 java8의 LocalDateTime는 하이버네이트가 알아서 매핑해줌

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]
}
