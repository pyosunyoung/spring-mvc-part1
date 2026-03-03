package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

//http://localhost:8080/request-param-v1?username=hello&age=20
@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamv1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age); //username=hello, age=20

        response.getWriter().write("ok"); //화면에 보여지는 부분
        //http://localhost:8080/request-param-v1
    }
     @ResponseBody
     @RequestMapping("/request-param-v2")
     public String requestParamv2(@RequestParam("username") String memberName,
                                @RequestParam("age") int memberAge) throws IOException {
        log.info("memberName={}, memberAge={}", memberName, memberAge);
        return "ok";


     }

     @ResponseBody
     @RequestMapping("/request-param-v3")
     public String requestParamv3(@RequestParam String username, // 파람 네임 생략 가능 대신 param name과 일치해야함.
                                  @RequestParam int age) throws IOException {
         log.info("memberName={}, memberAge={}", username, age);
         return "ok";


     }

     @ResponseBody
     @RequestMapping("/request-param-v4")
     public String requestParamv4(String username, int age) throws IOException { // RequestParam 까지 생략 가능
         log.info("memberName={}, memberAge={}", username, age);
         return "ok";


     }
     //HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
    //String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능
     // RequestParam을 생략하는것은 너무 가독성 저하라고 생각.

     @ResponseBody
     @RequestMapping("/request-param-required") //required는 생략 가능 기본값이 true임. false일시에만 사용
     public String requestParamRequired(@RequestParam(required = true)String username, // 파람 필수 여부
                                        @RequestParam(required = false)Integer age) throws IOException { // RequestParam 까지 생략 가능
         log.info("memberName={}, memberAge={}", username, age);
         return "ok";
//         null 을 int 에 입력하는 것은 불가능(500 예외 발생)
//         따라서 null 을 받을 수 있는 Integer 로 변경하거나, 또는 다음에 나오는 defaultValue 사용
// 주의 빈 문자까지 삽입 가능함.

     }

     @ResponseBody
     @RequestMapping("/request-param-default")  // 값이 안들어오면 기본값이 guest, -1
     public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest")String username, // 파람 필수 여부
                                        @RequestParam(required = false, defaultValue = "-1")Integer age) throws IOException { // RequestParam 까지 생략 가능
         log.info("memberName={}, memberAge={}", username, age);
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
         log.info("username={}, age={}", paramMap.get("username"),
                 paramMap.get("age"));
         return "ok";
     }

//     @ResponseBody // 과거 코드
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttributeV1(@RequestParam String username, @RequestParam int age) throws IOException {
//         HelloData helloData = new HelloData();
//         helloData.setUsername(username);
//         helloData.setAge(age);
//         log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
//         log.info("helloData={}", helloData);
//         return "ok";
//     }

    @ResponseBody
    @RequestMapping("/model-attribute-v1") // 현재 코드
    public String modelAttributeV1(@ModelAttribute HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/model-attribute-v2") // modelattribute 코드 생략 파트
    public String modelAttributeV2(HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);
        return "ok";
    }

//    String , int , Integer 같은 단순 타입 = @RequestParam
//            나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
//    참고
//    argument resolver는 뒤에서 학습한다.
    // 모델 안에 name을 넣는데 그건 view파일이 있을 떄 사용. 지금은 걍 넘어가자
}
