package jpabook.jpashop.service;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UpdateItemDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private int price;
    @NotEmpty
    private int stockQuantity;
}
