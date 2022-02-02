package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")//거울
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)// 꼭 EnumType.STRING를 쓰자!
    private DeliveryStatus statud; //READY, COMP
}
