package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Servlet Container 를 spring container 의 Bean 으로 등록 (추후 유연한 구성을 위해)
 */
public class HellobootApplication_v7 {
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        // 사용할 spring container 를 dispatcherServlet 의 생성시 넘겨주어야 한다.
        return new DispatcherServlet();
    }

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
//                dispatcherServlet.setApplicationContext(this);      // 생성자에서 spring container 를 넘겨주지 못했으니 이와 같은 방식으로 지정할 수 있으나, 해당 코드가 없어도 spring container 가 알아서 등록해준다.

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

/**
 * ApplicationContextAware 인터페이스를 구현한 클래스는 생성당시 set 메서드를 통해 applicationContext(spring container)가 자동으로 생성되며 주입된다.
 */