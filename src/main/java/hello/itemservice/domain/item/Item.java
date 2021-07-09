package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data는 핵심 도메인 만들 때는 상당히 위험하다. 예기치 않게 동작할 수 있기 때문이다!
//단순히 데이터를 주고받는 경우는 확인하고 @Data 사용 가능하기도 함!
//@Data 쓰려면 스펙 다 파악한 후 써야함!!
@Data
public class Item {
    private Long id;
    private String itemName;
    private Integer price;//price가 안들어갈 때도 있다고 가정하기 때문에 null도 받기 위해서
    private Integer quantity;

    public Item(){

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
