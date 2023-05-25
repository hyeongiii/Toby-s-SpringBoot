package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan      // @Component 가 붙은 모든 class 를 Bean object 로 등록한다.
public class HellobootApplication_v6 {

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
