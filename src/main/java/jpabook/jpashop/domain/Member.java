package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")//거울 //Order에 있는 member필드에 의해 매핑되었다
    private List<Order> orders = new ArrayList<>();//컬렉션은 필드에서 바로 초기화하는것이 안전함
                                                   //하이버네이트가 관리하는 컬렉션으로 감싸서 사용하기 때문에 임의로 컬렉션을 변경하지 말아라
}
