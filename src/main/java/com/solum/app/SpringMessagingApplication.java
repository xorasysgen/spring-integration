package com.solum.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import com.solum.app.logic.InitGracefulShutdown;

@SpringBootApplication
public class SpringMessagingApplication {

	@Bean
	public InitGracefulShutdown gracefulShutdown() {
	    return new InitGracefulShutdown();
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory(final InitGracefulShutdown initGracefulShutdown) {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addConnectorCustomizers(initGracefulShutdown);
	    return factory;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMessagingApplication.class, args);
	}


}
