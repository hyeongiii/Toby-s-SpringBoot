package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest 방식으로 HTML 을 통째로 반환하는 것 대신에 api 요청에 대한 응답을 body 에 특정한 타입으로 인코딩하여 보내는 controller 를 생성
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(String name) {
        return "Hello " + name;
    }
}

/**
 * API 를 통해 웹 클라이언트로부터 HTTP 요청을 받아 동적인 컨텐츠가 포함된 HTTP 응답을 생성한다.
 *
 * 이 때, API 테스트를 통해 HTTP 요청과 HTTP 응답이 개발자가 의도한대로 잘 이뤄지는지 확인하는 과정이 중요하다.
 *
 * HTTP 요청을 만들고 응답을 확인하는데 사용되는 도구
 * 1. 웹 브라우저 개발자 도구
 * 2. curl
 * 3. HTTPie
 * 4. Intellij IDEA - Ultimate http request
 * 5. JUnit Test
 * 6. postman
 */