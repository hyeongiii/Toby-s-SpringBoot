package tobyspring.helloboot;

import java.util.Objects;

public class HelloServletController {

    public String hello(String name) {
        SimpleHelloService helloService = new SimpleHelloService();

        // Objects.requireNonNull() : 매개변수로 넘겨준 객체가 NULL 일 경우, 예외를 던진다. (Null 인 경우를 방지하고, 아닐 경우 코드의 동작이 이어진다.)
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
/*
 - 웹 요청이나 웹 응답을 직접 노출하는 기술적인 type 을 사용하지 않음
    why? : 명확하긴 하지만, 평범한 자바 코드에 심플하게 넣을 수 있는 로직 사이사이에 웹 기술과 연동된 메서드들이 자주 등장하기 때문에 직접적으로 웹 요청과 응답을 다루는 object를 사용하지 않음
    대신, 평범한 자바 타입으로 웹 요청 정보를 변환해서 받음
 */