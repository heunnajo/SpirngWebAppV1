package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/{itemId}")//경로 변수 설정
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
//        return "basic/item_practice";
        return "basic/item";
    }
    //같은 url에 대해 get 들어오면 그냥 페이지만 보여주는 addForm 메서드 호출
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    //같은 url에 대해 post 방식 들어오면 저장하는 save 메서드 호출
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item",item);//상품 저장 후 상세 페이지 보여준다.
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        //model.addAttribute("item",item);//자동 추가, 생략 가능
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        //클래스명에서 앞글자만 소문자로 바꾼 것이 ModelAttribute 이름이 된다.
        //그래서 @ModelAttribute("item") 에서 괄호를 빼도 클래스명의 앞글자만 소문자인 것.
        itemRepository.save(item);
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV4(Item item){//요청파라미터를 가져올 때 파라미터가 객체이면 @ModelAttribute 생략 가능하다!
        itemRepository.save(item);
        return "basic/item";
    }
    //@PostMapping("/add")
    public String addItemV5(Item item){//요청파라미터를 가져올 때 파라미터가 객체이면 @ModelAttribute 생략 가능하다!
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){//요청파라미터를 가져올 때 파라미터가 객체이면 @ModelAttribute 생략 가능하다!
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);//status가 true이면 저장이 되서 넘어온 것!
        return "redirect:/basic/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);//뷰에 전달할 객체 item!
        return "basic/editForm";//editForm 페이지로 데이터 전달한다!
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }
    //테스트용 데이터
    @PostConstruct//초기화 자동 콜백함수:생성자 주입이 완료된후 실행된다.
    public void init(){
        itemRepository.save(new Item("chicken",10000,10));
        itemRepository.save(new Item("pizza",20000,20));
    }

//    @Autowired생성자가 하나기 때문에 생략가능!
//    public BasicItemController(ItemRepository itemRepository) {//생성자 주입
//        this.itemRepository = itemRepository;
//    }

}
