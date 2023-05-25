package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@RequestMapping
public class HelloServletController {
    private final HelloService helloService;

    // 의존성 주입 DI
    public HelloServletController(HelloService helloService) {
        this.helloService = helloService;
    }

    /**
     * 1. spring container 를 주입받은 dispatcherServlet 은 Bean 을 다 뒤진다.
     * 2. 빈 객체 중에서 웹 요청을 처리할 수 있는 mapping 정보를 가지고 있는 클래스를 찾는다. (@GetMapping)
     * 3. 해당 애노테이션을 통해 요청 정보를 추출해낸다.
     * 4. 매핑에 사용할 매핑 테이블을 만들고, 이후 요청이 들어오면 해당 테이블을 참고하여 매핑을 시켜준다.
     *
     * 메소드 레벨에 매핑과 관련된 애노테이션을 붙이면 dispatcherServlet 이 매번 찾을 수 없기 때문에, 클래스 레벨에도 명시해주어야 한다.
     * -> 클래스 레벨의 매핑 애노테이션을 통해 해당 클래스가 웹 요청을 처리할 수 있다는 정보를 주고 메소드 레벨의 애노테이션을 통해 정보를 추가하는 형태이다.
     *
     * @ResponseBody
     * : 컨트롤러의 반환값을 웹 응답의 body 에 넣어주는 역할
     */

    @GetMapping("/hello")
    @ResponseBody
    public String hello(String name) {
        // Controller 가 직접 Service 객체 생성
//        SimpleHelloService helloService = new SimpleHelloService();

        // Objects.requireNonNull() : 매개변수로 넘겨준 객체가 NULL 일 경우, 예외를 던진다. (Null 인 경우를 방지하고, 아닐 경우 코드의 동작이 이어진다.)
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
/*
 - 웹 요청이나 웹 응답을 직접 노출하는 기술적인 type 을 사용하지 않음
    why? : 명확하긴 하지만, 평범한 자바 코드에 심플하게 넣을 수 있는 로직 사이사이에 웹 기술과 연동된 메서드들이 자주 등장하기 때문에 직접적으로 웹 요청과 응답을 다루는 object를 사용하지 않음
    대신, 평범한 자바 타입으로 웹 요청 정보를 변환해서 받음
 */