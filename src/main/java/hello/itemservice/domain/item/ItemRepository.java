package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    //실무에서는 멀티스레드 사용하는 환경. 여러 스레드가 동시에 접근 가능한 동시성 문제. 싱글톤으로 생성되기 때문에!
    // 때문에 HashMap사용하면 안되고 ConcurrentHashMap 사용해야한다.
    // long 변수도 atomiclong 을 사용해야한다!
    private static final Map<Long,Item> store = new HashMap<>();//static
    private static long sequence = 0L;//static

    //아이템 저장
    public Item save(Item item){
        item.setId(++sequence);//@Data가 제공하는 자동 setter 기능으로 눈에 보이지는 않지만 Setter 존재!
        store.put(item.getId(),item);
        return item;
    }
    //아이템 조회
    public Item findById(Long id){
        return store.get(id);
    }
    //아이템 전체 목록 조회
    public List<Item> findAll(){
        //아이템 객체들 날 것 그대로 반환해도 되지만
        //값을 변경해도 ArrayList에 값을 넣고 원래 객체 데이터는 보존하기 위해
        //ArrayList로 한번 감싼다!
        return new ArrayList<>(store.values());//아이템 객체들 반환
    }
    //id가 사용되지 않기 때문에 updateParam라는 별도의 객체를 생성하는 것이 맞다!
    //개발자는 혼란스러워서 id필드가 있는데 왜 업데이트가 안되는지 의문을 가지며 setId로 값을 갱신해버릴 수도 있기 때문이다!
    public void update(Long itemId,Item updateParam){
        Item findItem = findById(itemId);

        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
    public void clearStore(){
        store.clear();
    }
}
