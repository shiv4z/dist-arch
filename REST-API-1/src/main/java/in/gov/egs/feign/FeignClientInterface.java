package in.gov.egs.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "REST-API")
public interface FeignClientInterface {
	
    @GetMapping("/welcome")
    String fetchWelcomeMsg(@RequestParam("name")String msg);

}
