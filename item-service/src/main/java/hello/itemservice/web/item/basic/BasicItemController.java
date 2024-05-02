package hello.itemservice.web.item.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * 1. @ModelAttribute - 요청 파라미터 처리
     *
     * @ModelAttribute 는 Item 객체를 생성하고, 요청 파라미터의 값을 프로퍼티 접근법(setXxx)으로 입력해준다.
     * <p>
     * 2. @ModelAttribute - Model 추가
     * @ModelAttribute 는 중요한 한가지 기능이 더 있는데,
     * 바로 모델(Model)에 @ModelAttribute 로 지정한 객체를 자동으로 넣어준다.
     * 지금 코드를 보면 model.addAttribute("item", item) 가 주석처리 되어 있어도 잘 동작하는 것을 확인할 수 있다.
     */
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);

//        model.addAttribute("item", item);
        // @ModelAttribute("item") 에서 해당 동작을 하고, Model 파라미터도 필요없다.

        return "basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        // @ModelAttribute 안의 이름을 생략하면,
        // 객체의 첫글자를 소문자로 바꾼 이름을 자동으로 사용한다.
        // ex) HelloData -> helloData

        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     * 단순 타입들은 @RequestParam가 적용되고,
     * 그 외는 @ModelAttribute 가 적용되는 것을 지난 시간에 학습했었다.
     */
//    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }
    /**
     *  ** 참고
     * HTML Form 전송은 PUT, PATCH를 지원하지 않는다. GET, POST만 사용할 수 있다.
     * PUT, PATCH는 HTTP API 전송시에 사용
     * 스프링에서 HTTP POST로 Form 요청할 때 히든 필드를 통해서 PUT,
     * PATCH 매핑을 사용하는 방법이 있지만, HTTP 요청상 POST 요청이다.
     */



    /**
     * PRG - Post/Redirect/Get
     * 상품 등록 후 새로고침은 마지막에 한 행위를 반복하는 것이다. -> 계속 Post 를 하는 효과
     * 그래서 Post 후에는 Redirect 해서 다른 페이지를 get 요청하는 것과 같이 동작하게 한다.
     */
//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
        // "redirect:/basic/items/" + item.getId() redirect에서 +item.getId() 처럼 URL에 변수를 더해서 사용하는 것은
        // URL 인코딩이 안되기 때문에 위험하다. 다음에 설명하는 RedirectAttributes 를 사용하자
        // 지금은 숫자라서 괜찮지만, 한글이나, 그런 문자들은 인코딩이 안된다.
    }

    /**
     * RedirectAttributes
     *
     * RedirectAttributes 를 사용하면 URL 인코딩도 해주고,
     * pathVariable , 쿼리 파라미터까지 처리해준다. -> pathVariable 바인딩: {itemId}
     * 나머지는 쿼리 파라미터로 처리: ?status=true
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
