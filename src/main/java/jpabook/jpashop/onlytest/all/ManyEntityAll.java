package jpabook.jpashop.onlytest.all;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ManyEntityAll {

    @Id @GeneratedValue
    @Column(name = "many_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_all_id")
    private OneEntityAll oneEntityAll;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "inside_id")
    private InsideEntityAll insideEntity;

    private String text;

    public void changeInsideEntity(String name) {
        getInsideEntity().setName(name);
    }
}
