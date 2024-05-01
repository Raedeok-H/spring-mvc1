package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    /**
     * @Controller 이면서, @ResponseBody 나 HttpEntity 를 쓰지 않고,
     * String 을 반환하면, View 의 논리 이름이 된다.
     * */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        return "response/hello";
    }


    // 반환이 Void 이면, 요청 URL 을 참고해서 논리 뷰 이름으로 사용한다.
    // 명시성이 없고, 딱 맞아 떨어지는 경우가 적어서 권장하지 않는다.
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }

}
