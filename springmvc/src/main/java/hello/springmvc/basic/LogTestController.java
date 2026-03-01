package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController//Rest api 느낌, 저 loggTest를 문자열 그대로 반환. 기존 controller는 view 이름을 반환한다??
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass()); // @Slf4j 있으면 이거 생략 가능

    @RequestMapping("/log-test")
    public String loggTest() {
        String name = "Spring";

        System.out.println("name = " + name);
        // , 대신 + 하면 연산이 일어나서 쓰면 안됨.
        log.trace(" info log={}", name); // 여러개 값 삽입 가능?
        log.debug(" info log={}", name); // 디버그시
        log.info(" info log={}", name); // 중요한 정보
        log.warn(" info log={}", name);// 경고
        log.error(" info log={}", name); // 에러
//        name = Spring 출력값
//        2026-03-01T21:55:38.954+09:00  INFO 34424 --- [springmvc] [nio-8080-exec-1] h.springmvc.basic.LogTestController      :  info log=Spring
// 기본적으론 info 부터 log를 찍음 trace는 application에서 설정하면 찍을 수 있음 info 레벨 부터 아래로 출력
        return "ok!";
//        LEVEL: TRACE > DEBUG > INFO > WARN > ERROR
//        개발 서버는 debug 출력
//        운영 서버는 info 출력
    }
}
