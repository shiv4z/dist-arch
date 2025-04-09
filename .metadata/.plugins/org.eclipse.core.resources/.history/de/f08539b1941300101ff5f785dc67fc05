package in.gov.egs.filter;

import java.time.Instant;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


@Component
public class HeaderValidationFilter extends AbstractGatewayFilterFactory<HeaderValidationFilter.Config> implements Ordered{

	public HeaderValidationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			var request = exchange.getRequest();

			String apiKey = request.getHeaders().getFirst("X-API-KEY");
			String secretKey = request.getHeaders().getFirst("X-SECRET-KEY");
			String timestamp = request.getHeaders().getFirst("X-TIMESTAMP");

			if (apiKey == null || secretKey == null || timestamp == null) {
				return unauthorizedResponse(exchange, "Missing required headers.");
			}

			if (!isValidTimestamp(timestamp)) {
				return unauthorizedResponse(exchange, "Request expired or invalid timestamp.");
			}

			return chain.filter(exchange);
		};
	}

	private boolean isValidTimestamp(String timestamp) {
		try {
			long requestTime = Long.parseLong(timestamp);
			long currentTime = Instant.now().toEpochMilli();
			long timeDiff = currentTime - requestTime;

			return timeDiff <= 300000;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().add("Content-Type", "application/json");
		var buffer = exchange.getResponse().bufferFactory().wrap(("{\"error\": \"" + message + "\"}").getBytes());
		return exchange.getResponse().writeWith(Mono.just(buffer));
	}

	public static class Config {
	}
	
	@Override
	public int getOrder() {
		return 1;
	}
}