package in.gov.egs.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyFilter implements GlobalFilter, Ordered {

	private static final String API_KEY = "X-API-KEY";
	private static final String SECRET_KEY = "X-SECRET-KEY";
	private static final String VALID_API_KEY = "rest-api-key-12345";
	private static final String VALID_SECRET_KEY = "rest-api-key-98765";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		System.out.println("Received API Key: " + exchange.getRequest().getHeaders().getFirst(API_KEY));
		System.out.println("Received Secret Key: " + exchange.getRequest().getHeaders().getFirst(SECRET_KEY));

		String apiKey = exchange.getRequest().getHeaders().getFirst(API_KEY);
		String secretKey = exchange.getRequest().getHeaders().getFirst(SECRET_KEY);

		if (!VALID_API_KEY.equals(apiKey) || !VALID_SECRET_KEY.equals(secretKey)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 2;
	}
}
