package jpabook.jpashop.onlytest.notall;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OneEntity {

    @Id @GeneratedValue
    @Column(name = "one_id")
    private Long id;

    @OneToMany(mappedBy = "oneEntity")
    private List<ManyEntity> manyEntities = new ArrayList<>();
}
