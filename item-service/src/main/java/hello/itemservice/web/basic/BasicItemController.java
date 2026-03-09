package hello.itemservice.web.basic;



import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     *  테스트용 데이터 추가
     */

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
