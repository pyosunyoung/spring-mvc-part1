package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") //v1 하위는 모두 호출이 되어 매핑한다.
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveController());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1()); //이 url이 들어오면 이 컨트롤러들을 해당 map에 추가
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service ");

        String requestURI = request.getRequestURI(); // 해당 접속된 url를 string으로 추출해서

//        ControllerV1 controller = new MemberFormControllerV1()); 아래는 사실 이렇게 들어오는거임. 다형성! ControllerV1이 부모이기 때문에 값 삽입 가능.
        ControllerV1 controller = controllerMap.get(requestURI); // 해당 url 컨트롤러를 조회
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response); // 해당 컨트롤러의 req, rep를 공통 process에 넘겨줌


    }
}
