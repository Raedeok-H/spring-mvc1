package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    // RequestMapping -> 해당 어노테이션은 주로 아래 3가지 방식으로 사용된다.
    // 1. GET 의                 쿼리파라미터 형식
    // 2. POST 의                HTML Form 에서 Message Body 의 쿼리파라미터 형식
    // 3. POST, PUT, PATCH 의    HTTP message body 에 JSON, XML, TEXT 형식
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.valueOf(request.getParameter("age"));
        log.info("username:{}, age:{}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("memberName:{}, memberAge:{}", memberName, memberAge);
        return "ok";    // 뷰를 찾는게 기본 동작이기 때문에
        // 클래스를 RestController 로 바꾸던지,
        // 메소드 위에 ResponseBody 를 달아줘야 함
    }

    // HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능이지만,
    // 빌드 툴 설정을 gradle 이 아닌 intellij 로 해놨으면 안될 수도 있음
    // 주로 다음 두 애노테이션에서 문제가 발생한다. @RequestParam , @PathVariable
    //  -> https://www.inflearn.com/questions/1088283/pathvariable-%EB%B3%80%EC%88%98%EB%AA%85-%EA%B0%99%EC%9D%84%EB%95%8C-%EC%83%9D%EB%9E%B5%EC%8B%9C-%EC%98%A4%EB%A5%98-%EB%B9%8C%EB%93%9C-%EC%84%A4%EC%A0%95%EC%9D%84-gradle%EB%A1%9C-%ED%95%98%EB%A9%B4-%ED%95%B4%EA%B2%B0%EB%90%98%EB%8A%94-%EA%B2%83-%EA%B0%99%EC%8A%B5%EB%8B%88%EB%8B%A4
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {

        log.info("username:{}, age:{}", username, age);
        return "ok";
    }

    //String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username,
                                 int age) {

        log.info("username:{}, age:{}", username, age);
        return "ok";
    }


    // todo: 근데 생략 안하고 v1 처럼하는 습관을 들이는 것이 좋다


    /**
     * @RequestParam.required /request-param-required -> username이 없으므로 예외
     * <p>
     * 주의!
     * /request-param-required?username= -> 빈문자로 통과
     * 주의!
     * <p>
     * /request-param-required
     * int age -> null을 int에 입력하는 것은 불가능,
     * 따라서 Integer 변경해야 함(또는 다음에 나오는defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, // 기본은 필수
                                       @RequestParam(required = false) Integer age) { // 값이 없으면 null 이 들어오는데,
        // null 과 "" 는 다름 -> 빈문자가 들어와도 들어온 것으로 인식                                                                             // int 타입은 0이라도 넣어줘야해서, Integer 를 써야한다.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam - defaultValue 사용
     * <p>
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault( // 이미 기본 값이 있기 때문에 required 는 의미가 없다.
                                       @RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") int age) {
        // 빈문자("") 도 defaultValue 로 대체한다.
        log.info("username={}, age={}", username, age);
        return "ok";
    }


    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...]) ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }
    /*
    * 파라미터를 Map, MultiValueMap으로 조회할 수 있다.
    @RequestParam Map ,
        Map(key=value)

    @RequestParam MultiValueMap
        MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1,id2])

    파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자.
    근데 거의 1개 쓴다.
    * */


    /**
     * @ResponseBody
     *     @RequestMapping("/model-attribute-v1")
     *     public String modelAttributeV1(@RequestParam String username, @RequestParam int age) {
     *         HelloData helloData = new HelloData();
     *         helloData.setUsername(username);
     *         helloData.setAge(age);
     *
     *         log.info("username:{}, age:{}", helloData.getUsername(), helloData.getAge());
     *         log.info("helloData={}", helloData); // toString() 메소드를 사용
     *
     *         return "ok";
     *     }
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때 자세히 설명
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username:{}, age:{}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData); // toString() 메소드를 사용

        return "ok";
    }
    // 바인딩 오류
    // age=abc 처럼 숫자가 들어가야 할 곳에 문자를 넣으면 BindException 이 발생한다.
    // 이런 바인딩 오류를 처리하는 방법은 검증 부분에서 다룬다




    /**
     * @ModelAttribute 생략 가능
     *
     * 스프링에서 아래처럼 처리한다.
     * String, int 같은 단순 타입 = @RequestParam 으로 사용
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute 으로 사용
     *
     * argument resolver -> HttpServletRequest 같은 것임
     * 기본적으로 사용자가 만든 모델의 경우 @ModelAttribute 가 적용된다고 생각하면 됨.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }



}
