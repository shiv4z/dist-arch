package in.gov.egs.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;


@Component
public class DynamicPortInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        String appName = environment.getProperty("spring.application.name", "APP");
        String port = environment.getProperty("server.port");

        if (port != null) {
            String serviceUrl = "http://localhost:" + port;
            System.setProperty("spring.boot.admin.client.instance.service-url", serviceUrl);
            System.setProperty("eureka.instance.instance-id", appName + ":" + port);
        }
    }
}