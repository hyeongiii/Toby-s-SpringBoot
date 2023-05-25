package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootApplication_v4 {
    public static void main(String[] args) {
        // dispatcherServlet 에는 webApplicationContext 를 파라미터로 넘겨줘야 한다.
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("dispatcherServlet",
                    // DispatcherServlet 이 mapping 을 하다가 작업을 위임할(dispatch 할) object 를 찾을 spring container 를 파라미터로 넘김
                    new DispatcherServlet(applicationContext)
            ).addMapping("/*");
        });
    }
}
