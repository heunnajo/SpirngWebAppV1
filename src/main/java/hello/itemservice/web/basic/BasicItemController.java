package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller//BasicItemController가 스프링 빈으로 등록.
@RequestMapping("/basic/items")
@RequiredArgsConstructor//final 붙은 애들 생성자 만들어준다!
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }
    //테스트용 데이터
    @PostConstruct//초기화 자동 콜백함수:생성자 주입이 완료된후 실행된다.
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

//    @Autowired생성자가 하나기 때문에 생략가능!
//    public BasicItemController(ItemRepository itemRepository) {//생성자 주입
//        this.itemRepository = itemRepository;
//    }

}
