package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.dto.ItemUpdateDto;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.item.Book;
import java.util.List;

@Service
@Transactional(readOnly =  true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    @Transactional
    public void updateItem(Long itemId, int price, String name, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
    }

    @Transactional
    public void updateItem2(Long itemId, ItemUpdateDto dto){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(dto.getPrice());
        findItem.setName(dto.getName());
        findItem.setStockQuantity(dto.getStockQuantity());
    }


    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
