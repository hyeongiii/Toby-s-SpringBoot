package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring Container 를 초기화하는 과정에서 dispatcherServlet 을 초기화하도록 통합 (SpringBoot 의 동작 방식)
 *
 * @Configuration : BeanFactory Method(@Bean 애노테이션이 붙은 메서드)가 있다고 spring container 에게 정보를 제공
 * -> spring container 은 @Configuration 애노테이션이 붙은 클래스에서 @Bean 이 붙은 메서드를 찾아 빈 object 를 생성한다.
 */
@Configuration
public class HellobootApplication_v5 {

    @Bean
    public HelloServletController helloServletController(HelloService helloService) {
        return new HelloServletController(helloService);
    };

    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }


    public static void main(String[] args) {
        // 애노테이션으로 config 정보를 읽을 수 있는 spring container
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*");
                });
                // 톰캣 서블릿 컨테이너 동작
                webServer.start();
            }
        };
        applicationContext.register(HellobootApplication_v5.class);
        applicationContext.refresh();


    }
}
