package jpabook.jpashop.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ItemUpdateDto {
    private String name;
    private int price;
    private int stockQuantity;
}
