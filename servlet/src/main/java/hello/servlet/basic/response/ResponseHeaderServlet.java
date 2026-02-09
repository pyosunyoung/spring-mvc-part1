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
        //{status-line}
        response.setStatus(HttpServletResponse.SC_OK); // SC_OK는 200
//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 에러

        //[response-headers]
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 제거
        response.setHeader("Pragma", "no-cache"); // 과거? 캐시 제거
        response.setHeader("my-header", "hello"); // 임의의 헤더도 만들 수 있음.
        //개발자도구 responseHeader에서 확인가능.
        //[Header의 편의 메서드]
//        content(response);

        //[message body]
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세연");
    }

    //편의 메서드
    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    //쿠키 편의 메서드
    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

//    redirect 편의 메서드
    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html"); //302 -> 200으로 /basic/hello-form.html 리다이렉트
    }
}
