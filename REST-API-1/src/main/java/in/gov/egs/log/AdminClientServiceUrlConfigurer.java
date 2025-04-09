package in.gov.egs.log;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AdminClientServiceUrlConfigurer implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int actualPort = event.getWebServer().getPort();
        System.setProperty("spring.boot.admin.client.instance.service-url", "http://localhost:" + actualPort);
    }
}
