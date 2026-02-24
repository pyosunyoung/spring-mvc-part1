package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.coyote.http11.Constants.a;
import static org.apache.taglibs.standard.lang.jstl.ImplicitObjects.createParamMap;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private Map<String, Object> handlerMappingMap= new HashMap<>(); // 이젠 컨트롤러가 다 들어가야하니 Object로 ㄱ
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>(); // 어뎁터들 사이에서 가져와야하니 list

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();

    }


    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service ");
        //MemberFormControllerV4
        Object handler = getHandler(request); // 1. 핸들러 찾아와

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //ControllerV4HandlerAdapter 반환
        MyHandlerAdapter adapter = getHandlerAdapter(handler); // 2. 핸들러 어뎁터 찾아와

        ModelView mv = adapter.handle(request, response, handler); // 3.찾은 해당 handler를 넘겨줘서 핸들러 어댑터 호출.

        String viewName = mv.getViewName();
        // 현재는 논리이름 new-form만 추출됨.
        MyView view = viewResolver(viewName);// 이렇게 해주면 경로가 합쳐저서 동작 가능하게 됨. => 메서드로 추출.
        view.render(mv.getModel() ,request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // 해당 접속된 url를 string으로 추출해서
        return handlerMappingMap.get(requestURI); // 해당 매핑 map에서 url 컨트롤러를 조회
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        //MemberFormControllerV4
        for (MyHandlerAdapter adapter : handlerAdapters) { //V3 거치고 없으면 V4를 찾음 핸들러를
            if (adapter.supports(handler)) { // support에서
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp"); // 물리 이름으로 만들어버림.
    }
}
