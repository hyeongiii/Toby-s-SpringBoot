package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;

public class HellobootApplication {

	public static void main(String[] args) {
		// 톰캣 서블릿 웹서버의 설정 및 생성을 도와주는 클래스
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		// 웹 서버(서블릿 컨테이너) 생성 함수 : getWebServer()
		WebServer webServer = serverFactory.getWebServer();
		// 톰캣 서블릿 컨테이너 동작
		webServer.start();
	}

}

// 서블릿 컨테이너 대표적 예시 : 톰캣
