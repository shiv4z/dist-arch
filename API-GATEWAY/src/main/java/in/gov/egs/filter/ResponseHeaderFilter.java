package in.gov.egs.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ResponseHeaderFilter extends AbstractGatewayFilterFactory<ResponseHeaderFilter.Config> implements Ordered{

    public ResponseHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();

            response.getHeaders().add("X-Content-Type-Options", "nosniff");                     
            response.getHeaders().add("X-Frame-Options", "DENY");                               
            response.getHeaders().add("X-XSS-Protection", "1; mode=block");                      
            response.getHeaders().add("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");  
            response.getHeaders().add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");             
            response.getHeaders().add("Pragma", "no-cache");                                   
            response.getHeaders().add("Referrer-Policy", "no-referrer");                       
            response.getHeaders().add("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self'");  
        }));
    }

    public static class Config {
    	
    }
    
    @Override
	public int getOrder() {
		return 3;
	}
}