package hello.itemservice.domain;

import lombok.Data;

@Data // 게터 세터 생성 가능. 이건 사용하는건 위험하다
//@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity; // Integer를 쓴 이유는 null이 들어갈 경우도 대비해서

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) { //id를 제외한 생성자
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
