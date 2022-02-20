package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, UpdateItemDto itemDto) {
        //변경 감지 방식으로 update
        Item item = itemRepository.findOne(itemId);
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        item.setStockQuantity(itemDto.getStockQuantity());
        //트랜젝션이 끝나고 flush를 해서 DB에 변경된 내용이 반영됨

        //그리고 위와같이 setter로 값을 변경하는것은 유지보수하기 영 좋지않음(값이 변경되는 지점을 추적하기 어렵기때문)
        //item.change(name, price, stockQuantity) 처럼 Entity에 변경 메소드를 만들어서 관리하는것이 좋음
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
