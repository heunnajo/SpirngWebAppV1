package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach//테스트 끝날 때마다 초기화해준다!해
    void afterEach(){
        itemRepository.clearStore();
    }
    @Test
    void save(){
        //given
        Item item = new Item("chicken",20000,10);
        //when
        Item savedItem = itemRepository.save(item);
        //then : 상품이 제대로 저장되있으면 itemRepository에서 id로 조회가능해야한다!
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }
    @Test
    void findAll(){
        //given
        Item item1 = new Item("chicken",20000,10);
        Item item2 = new Item("TTeokbokki",18000,5);
        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> result = itemRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);
    }
    @Test
    void updateItem(){
        //given
        Item item1 = new Item("chicken",20000,10);
        Item savedItem = itemRepository.save(item1);
        Long itemId = savedItem.getId();

        //when : 수정
        Item updateParam = new Item("PIZZA", 30000, 100);
        itemRepository.update(itemId,updateParam);

        //then
        //생성한 item1의 ID로 조회해서 findItem 얻기=>그냥 바로 item1 객체 인스턴스 가져다 쓰면 안되나?
        Item findItem = itemRepository.findById(itemId);
        //findItem의 상품이 름과 updateParam의 상품 이름과 같은지 비교
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}
