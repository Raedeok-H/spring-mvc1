package hello.itemservice.domain.item;

import lombok.*;

//@Data // 핵심 도메인에 위험하다 -> 웬만하면 필요한 것만 골라 쓰자
@Data // 근데 예제니까 그냥 쓴다.
@NoArgsConstructor
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    // null 값이 들어올 수도 있으면 int 말고 Integer 로 하자.
    // 기본값이 0인 int 를 쓰기 애매하면 Integer 를 쓰자

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
