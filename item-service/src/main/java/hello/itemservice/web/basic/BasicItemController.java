package hello.itemservice.web.basic;



import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired 생성자가 1개면 생략 가능, 스프링 bean에 등록하는 단계
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    } //@RequiredArgsConstructor이거로 위에 생성자 로직 대체 가능.

    @GetMapping
    public String items(Model model) { // 모든 아이템 목록 반환
         List<Item> items = itemRepository.findAll();
         model.addAttribute("items", items);
         return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable("itemId") Long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //같은 url인데 http 메서드로 구분
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add") //RequestParma 생략 시 오류나서 작성해주는 것이 좋다. 즉 파라미터 이름을 명시해야 한다.
    public String addItemV1(@RequestParam("itemName") String itemName,
                       @RequestParam("price") int price,
                       @RequestParam("quantity") Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item); // 실제 파람이 오면 아이템 객체를 생성하고 그것을 저장소에 저장한다.

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add") //ModelAttribute 버전
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        //1. item 객체를 만들고, name, price, quantity 값을 세팅하는 것을 모두 생략 가능.
        //@ModelAttribute("item")의  ("item")이게 뭐냐? model.addAttribute("item", item);을 대신해주는 느낌이라 아래 코드 생략 가능
        itemRepository.save(item);
//        model.addAttribute("item", item); //2. 모델에 자동 추가로 생략 가능.
        return "basic/item";
    }


    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) { //Item -> item 소문자로 바꿔서 명시 또한 생략 가능.
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
    @PostMapping("/add") // 이거까지 줄이는 것은 좀 애매함.
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }


    /**
     *  테스트용 데이터 추가
     */

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
