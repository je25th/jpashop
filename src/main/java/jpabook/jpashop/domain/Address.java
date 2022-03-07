package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter//값타입은 변경불가능하도록 만들어야함!(Setter금지)
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {//JPA스펙상 기본 생성자가 필요
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
