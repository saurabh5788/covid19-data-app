package com.ssingh.covid19;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

import com.ssingh.covid19.service.EmailService;

@SpringBootApplication
@EnableAsync
@EnableEurekaClient
public class ApplicationConfig implements ApplicationRunner {

	@Autowired(required = false)
	private EmailService emailService;
	
	@Value("${spring.cloud.client.hostname:LOCAL}")
	private String applicationHost;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${spring.application.instance_id:NO_INSTANCE}")
	private String applicationInstanceId;
	
	@Value("${server.port}")
	private String serverPort;
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfig.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (emailService != null) {
			StringBuilder message = new StringBuilder();
			message.append("Host : ").append(applicationHost);
			message.append("\nApplication Name : ").append(applicationName);
			message.append("\nApplication Instance : ").append(applicationInstanceId);
			message.append("\nApplication Port : ").append(serverPort);
			emailService.sendMail("saurabh_57@hotmail.com", "Application up",message.toString());			
		}
		
	}

}

