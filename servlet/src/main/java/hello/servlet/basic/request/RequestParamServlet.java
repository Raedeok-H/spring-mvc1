package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 * 
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회] - start");

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println("paramName = " + request.getParameter(paramName)));

        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();

        //getParameter 는 GET 방식의 URL 쿼리 파라미터 방식도 지원하고,
                        //POST HTML Form 형식 둥 다 지원한다.
                        // => 폼으로 데이터를 전송하는 형식을 application/x-www-form-urlencoded 라 한다.
        // 간단한 form 은 Postman에서 application/x-www-form-urlencoded 로 테스트할 수 있다.
        System.out.println("[단일 파라미터 조회] - start");
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();
        System.out.println("[단일 파라미터 조회] - end");

        // ex) username=hello&username=hello2 처럼 여러개를 보낼 때
        // 중복으로 보내는 경우는 거의 없음
        System.out.println("[이름이 같은 복수 파라미터 조회] - start");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }

        System.out.println("[이름이 같은 복수 파라미터 조회] - end");

        response.getWriter().write("ok");
    }
}
