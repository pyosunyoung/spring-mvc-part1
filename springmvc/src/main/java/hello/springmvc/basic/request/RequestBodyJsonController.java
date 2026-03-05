package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
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
import java.nio.charset.StandardCharsets;


/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper(); // json타입이기 때문에 필요

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);// 2번쨰 인자는 타입., 자바 객체로 변경해주는 단계

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        response.getWriter().write("ok");
    }
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); // 이거 굳이 바꿔줘야함 객체로ㅓ? 이것도 간편하게 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3") // Request body에 직접만든 객체 지정 가능.
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException { // 타입을 바로 정의 가능

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        //httpmessage 컨버터가 여기 생략된 부부 코드를 대신 실행시켜주는 것, 그렇게 생성된 hellodata를 넣어준것. 즉 json도 객체로 변경해줌.
        return "ok";
        //@RequestBody를 생략 시 @modelAttribute가 적용 되어버려서 안됨 즉 메시지 바디가 아니 요청 파라미터를 처리하게됨.
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4") // Request body에 직접만든 객체 지정 가능.
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException { // 타입을 바로 정의 가능
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5") // Request body에 직접만든 객체 지정 가능.
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException { // 타입을 바로 정의 가능
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data; // json이 객체가 되었다가 객체가 나갈 떄 다시 json이 되어서 아래 처럼 출력됨.
        //{
        //    "username": "hello",
        //    "age": 20
        //}
    }
}
//@RequestBody 요청
//JSON 요청 => HTTP 메시지 컨버터 => 객체 , v5 예시처럼 Requestbody에 지정한 객체를 지정하여 객체로 반환 가능.
//@ResponseBody 응답
//객체 => HTTP 메시지 컨버터 => JSON 응답 , v5 예시 처럼 HelloData로 반환하면 json으로 응답
