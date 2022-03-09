package jpabook.jpashop.onlytest.notall;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ManyEntity {

    @Id @GeneratedValue
    @Column(name = "many_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_id")
    private OneEntity oneEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inside_id")
    private InsideEntity insideEntity;

    private String text;

    public void changeInsideEntity(String name) {
        getInsideEntity().setName(name);
    }
}
