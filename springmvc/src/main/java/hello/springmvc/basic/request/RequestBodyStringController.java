package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyString2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3") // httpbody에 있는 것을 문자로 바꿔서 넣어주는 느낌, http message 컨버터 라고도 함.
    public HttpEntity<String> requestBodyString3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();// getHeader등 직접 조회 가능, 얜는 메시지 바디 정보를 직접 조회하는 기능임.
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); 이거 생략 가능해짐.
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }
//    HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다.
//    RequestEntity
//    HttpMethod, url 정보가 추가, 요청에서 사용
//    ResponseEntity
//    HTTP 상태 코드 설정 가능, 응답에서 사용
//return new ResponseEntity<String>("Hello World", responseHeaders,
//    HttpStatus.CREATED)

    //가장 간결해진 코드 이것을 가장 많이 씀.
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyString4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        return "ok";
    }


}



//요청 파라미터는
//get 쿼리 스트링 파라미터 데이터
//post html 폼 전송 시,  requestParam, modelAtribut사용
//
//나머진 다 http entity 사용하는듯

//요청 파라미터 vs HTTP 메시지 바디
//요청 파라미터를 조회하는 기능: @RequestParam , @ModelAttribute
//HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody