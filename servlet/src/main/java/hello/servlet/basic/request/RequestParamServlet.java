package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//* 1. 파라미터 전송 기능
// * http://localhost:8080/request-param?username=hello&age=20
//        * <p>
// * 2. 동일한 파라미터 전송 가능
// * http://localhost:8080/request-param?username=hello&username=kim&age=20
//        */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회] - start");

        request.getParameterNames().asIterator().forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName))); // 모든 파라미터 추출 가능.
        // paraName은 key갑스 request.getParameter에 key값을 대입해서 value 가져올 수 있음.
        System.out.println("[전체 파라미터 조회] - end");
        
        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        
        System.out.println("username = " + username);
        String age = request.getParameter("age");
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] userNames = request.getParameterValues("username"); // get URL 쿼리 파라미터 형식도 지원하고 , post html form 형식도 둘 다 지원함. 둘다 가져옴.
        for (String name : userNames) {
            System.out.println("name = " + name);
        }
        System.out.println("test");

        response.getWriter().write("ok");
    }
}
