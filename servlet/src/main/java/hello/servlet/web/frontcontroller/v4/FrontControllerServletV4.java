package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*") //v1 하위는 모두 호출이 되어 매핑한다.
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4()); //이 url이 들어오면 이 컨트롤러들을 해당 map에 추가
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service ");

        String requestURI = request.getRequestURI(); // 해당 접속된 url를 string으로 추출해서

//        ControllerV2 controller = new MemberFormControllerV1()); 아래는 사실 이렇게 들어오는거임. 다형성! ControllerV1이 부모이기 때문에 값 삽입 가능.
        ControllerV4 controller = controllerMap.get(requestURI); // 해당 url 컨트롤러를 조회
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //paramMap
        Map<String, String> paramMap = createParamMap(request);

        // 반환결과 new MyView("/WEB-INF/views/members.jsp");
        Map<String, Object> model = new HashMap<>(); // 이부분 추가 v3 -> v4
        String viewName = controller.process(paramMap, model);

//        ModelView mv = controller.process(paramMap);
//        String viewName = mv.getViewName();
//        // 현재는 논리이름 new-form만 추출됨.

        MyView view = viewResolver(viewName);// 이렇게 해주면 경로가 합쳐저서 동작 가능하게 됨. => 메서드로 추출.
        view.render(model ,request, response); // 직접 model을 넣어줌.


    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp"); // 물리 이름으로 만들어버림.
    }
}
