package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
			HelloServletController helloServletController = new HelloServletController();

			// 익명 클래스의 람다식 전환 / 서블릿 컨테이너에 웹 요청을 처리하는 서블릿 등록
			servletContext.addServlet("frontcontroller", new HttpServlet() {    // front-controller : 모든 요청의 중앙 처리/ 서블릿 컨테이너의 mapping 기능 수행
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통 기능 처리

					if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						String name = req.getParameter("name");

						// frontController 가 아닌 helloServletController 의 hello 메서드에 작업을 위임
						String ret = helloServletController.hello(name);

						resp.setStatus(HttpStatus.OK.value());		// 하드코딩(직접 작성)보다는 spring이 제공하는 ENUM으로!
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().print(ret);
					}
					else if (req.getRequestURI().equals("/user")) {
						//
					}
					else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}
				}
			}).addMapping("/*");

		});
		// 톰캣 서블릿 컨테이너 동작
		webServer.start();
	}

}

/*

 - 서블릿 컨테이너 대표적 예시 : 톰캣

 - front controller 의 중요한 역할
 	1. mapping : 웹 요청에 들어온 정보를 파악하여 어떤 로직을 수행하는 코드를 호출할 것인지 결정하는 작업 (getRequestURI())
 	2. binding : 파라미터로 넘어온 값을 평범한 자바 타입으로 변환하여 controller 에 넘겨주는 작업
 				 웹 요청을 가지고, 그것을 처리하는 로직 코드에서 사용할 수 있도록 새로운 형태의 타입으로 변환하는 작업
 				 Form 을 통해 많은 정보가 넘어왔을 경우, DTO / JAVA Bean 형태의 Object 로 만들어서 데이터를 집어넣어 처리하는 것
 */
