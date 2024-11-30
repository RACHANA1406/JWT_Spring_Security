package com.example.spring_security.jwt;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.spring_security.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	@Value("${jwt.secret_key}")
	private String secret_key;
	
	@Value("${jwt.expiration}")
    private long expiration;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public boolean isTokenValid(String token, UserDetails employee) {
		String username=extractUsername(token);
		return username.equals(employee.getUsername()) && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date(System.currentTimeMillis()));
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims=extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}
	
	public String generateToken(Employee employee) {
		String token= Jwts
				.builder()
				.subject(employee.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSecretKey())
				.compact();
		return token;
	}
	
	private SecretKey getSecretKey() {
		byte[] keyBytes=Decoders.BASE64URL.decode(secret_key);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
