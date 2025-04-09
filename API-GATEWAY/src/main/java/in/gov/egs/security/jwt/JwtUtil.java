package in.gov.egs.security.jwt;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import in.gov.egs.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expiration}")
	private int jwtExpirationMs;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	public String generateToken(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		Map<String, Object> claims = new HashMap<>();
		claims.put("roles",
				userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

		return Jwts.builder().setClaims(claims)
				             .setSubject(username)
				             .setIssuedAt(new Date(0))
				             .setIssuer("api-gateway")
				             .setAudience("your-frontend-domain")
				             .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				             .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
				   .build()
				   .parseClaimsJws(token)
				   .getBody()
				   .getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
			    .requireIssuer("api-gateway")
			    .build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
}