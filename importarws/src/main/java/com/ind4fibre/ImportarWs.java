package com.ind4fibre;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ImportarWs {

	public static void main(String[] args) {
		SpringApplication.run(ImportarWs.class, args);
	}
	
	@Configuration
	public class MvcConfig implements WebMvcConfigurer {

		@Override
		public void addViewControllers( ViewControllerRegistry registry) {
			registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
		}
		
	}
}

@Component
class MyBean {

    @Value("${spring.datasource.url}")
    private String dataSourceConnection;
    
    @Value("${ftp.host}")
    private String ftpConnection;

    public MyBean() {

    }

    @PostConstruct
    public void init() {
        System.out.println("================== " + dataSourceConnection + "================== ");
        System.out.println("================== " + ftpConnection + "================== ");
    }
}


