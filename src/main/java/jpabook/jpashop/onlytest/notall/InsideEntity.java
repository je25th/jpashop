package jpabook.jpashop.onlytest.notall;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class InsideEntity {

    @Id @GeneratedValue
    @Column(name = "inside_id")
    private Long id;

    private String name;
}
