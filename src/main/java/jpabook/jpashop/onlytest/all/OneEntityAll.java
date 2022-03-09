package jpabook.jpashop.onlytest.all;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class OneEntityAll {

    @Id @GeneratedValue
    @Column(name = "one_all_id")
    private Long id;

    @OneToMany(mappedBy = "oneEntityAll", cascade = CascadeType.ALL)
    private List<ManyEntityAll> manyEntityAlls = new ArrayList<>();
}
