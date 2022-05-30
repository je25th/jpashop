package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)//거울
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)// 꼭 EnumType.STRING를 쓰자!
    private DeliveryStatus statud; //READY, COMP
}
