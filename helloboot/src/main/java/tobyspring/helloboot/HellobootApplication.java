package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootApplication {
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public static void main(String[] args) {
        // SpringBoot 가 프로젝트를 처음 시작할 때 만들어주는 코드 -> spring container 를 띄우고 servlet container 를 띄우고 의존관계를 주입해줄 수 있는 이유
        MySpringApplication.run(HellobootApplication.class, args);
    }


}
