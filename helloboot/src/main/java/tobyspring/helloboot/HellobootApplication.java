package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		// 톰캣 서블릿 웹서버의 설정 및 생성을 도와주는 클래스
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		// 웹 서버(서블릿 컨테이너) 생성 함수 : getWebServer(new ServletContextInitializer() {})
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			// 익명 클래스의 람다식 전환 / 서블릿 컨테이너에 웹 요청을 처리하는 서블릿 등록
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					String name = req.getParameter("name");

					resp.setStatus(HttpStatus.OK.value());		// 하드코딩(직접 작성)보다는 spring이 제공하는 ENUM으로!
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
					resp.getWriter().print("Hello Servlet");
				}
			}).addMapping("/hello");

		});
		// 톰캣 서블릿 컨테이너 동작
		webServer.start();
	}

}

// 서블릿 컨테이너 대표적 예시 : 톰캣
