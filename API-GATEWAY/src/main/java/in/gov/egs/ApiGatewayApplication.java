package in.gov.egs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"in.gov.egs", "in.gov.egs.conig"})
public class ApiGatewayApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApiGatewayApplication.class, args);
		BCryptPasswordEncoder encoder = context.getBean(BCryptPasswordEncoder.class);
		System.out.println(encoder.encode("shiv@123"));
	}

}
