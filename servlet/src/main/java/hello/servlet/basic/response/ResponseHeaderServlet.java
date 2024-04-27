package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [status-line]
        response.setStatus(HttpServletResponse.SC_OK); // 200 보다 이렇게 해주는 것이 좋음
        // 400 은 SC_BAD_REQUEST 임

        //[response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8"); // utf-8로 설정하면 응답으로 한글도 가능
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello"); // 이렇게 임의의 헤더도 설정할 수 있음

        // [Header 편의 메서드]
        content(response);

        // [Cookie 편의 메서드]
        cookie(response);

        // [Cookie 편의 메서드]
        redirect(response);


        // [message body]
        PrintWriter writer = response.getWriter();
//        writer.print("안녕하세요");
        writer.print("ok");
    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");

        // 위처럼 해도 되지만 아래처럼 cookie 객체를 사용할 수도 있음
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }
    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html

        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");

        // 위 두줄처럼 처리해도 되지만 아래처럼 처리 가능
        response.sendRedirect("/basic/hello-form.html");
    }

}
