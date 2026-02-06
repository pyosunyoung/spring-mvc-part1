package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
//name은 서블릿 이름, url은 url 매핑 이 두개는 서로 문자열이 겹치면 안됨.
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override // 컨트롤 o -> service, 로직 : servlet이 호출되면 service 메소드가 호출됨.
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);  // 서블릿 컨테이너에서 request, response 객체를 생성
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username); // username = kim

        response.setContentType("text/plain;charset=UTF-8"); // content type
        response.getWriter().write("Hello " + username); // response body의 메시지로 들어감.
        //개발자 도구 response header에서 확인 가능

    }
}
