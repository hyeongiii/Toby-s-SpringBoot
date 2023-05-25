package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// spring container 생성 및 Bean 정보 등록
public class HellobootApplication_v2 {

    public static void main(String[] args) {
        // spring application context -> spring container
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // spring container 에 bean 등록
        applicationContext.registerBean(HelloController.class);
        // bean 구성정보를 사용하여 spring container 초기화
        applicationContext.refresh();

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("hello", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        // spring container 로부터 Bean 객체 (정보) 생성 -> spring container 가 가지고있는 object 를 전달받은 다음 servlet 이 사용한다.
                        HelloController helloController = applicationContext.getBean(HelloController.class);
                        String ret = helloController.hello(name);

                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().print(ret);
                    }
                    else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            });
        });
    }
}

/**
 * 서블릿 컨테이너는 HelloController 객체가 어떻게 만들어졌는가에 대해서는 신경쓰지 않는다.
 * FrontController 는 요청을 mapping 및 binding 해주지만, 실질적인 처리는 spring container 내부의 Bean 객체인 Hello Controller 가 수행한다.\\
 *
 * spring container 는 bean 에 등록된 객체(필요한 객체)를 딱 한번만 생성한다.
 * 즉, 추후에 다른 servlet 이 추가되더라도 spring container 는 동일한 객체를 반환한다.(object 재사용) -> (싱글톤 패턴)
 * 이 때문에 spring container 가 singleton registry 라고도 불리낟.
 */