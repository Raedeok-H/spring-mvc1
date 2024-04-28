package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    //    @RequestMapping(value = "/new-form", method = RequestMethod.GET) // GET 으로 올 때만 매핑
    @GetMapping(value = "/new-form") // GET 으로 올 때만 매핑
    public String newForm() {
        return "new-form";
    }

    //    @RequestMapping(value = "/save", method = RequestMethod.POST) // POST 로 올 때만 매핑
    @PostMapping(value = "/save") // POST 로 올 때만 매핑
    public String save(
            @RequestParam("username") String username, // request 에서 특정 파라미터를 받아옴
            @RequestParam("age") int age,   // 물론 GET 쿼리 파라미터, POST Form 방식을 모두 지원한다
            Model model // Model을 파라미터로 받는 스프링 MVC의 편의 기능
    ) {
        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);

        return "save-result"; // ModelAndView 가 아닌 뷰의 논리 이름을 반환할 수 있다.
    }

    //    @RequestMapping(method = RequestMethod.GET) // GET 으로 올 때만 매핑
    @GetMapping // GET 으로 올 때만 매핑
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);

        return "members";
    }

}
