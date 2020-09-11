package jpabook.jpashop.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemUpdateDto {
    private String name;
    private int price;
    private int stockQuantity;
}
