package com.team2.fithub.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private String key = "SangYong_JinWoo_MinHyeok_SecretKey"; // jwt key
	private SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	
	private long refreshTokenExpireTime = 60*60*24*14; // 2주간 유효
	
	public String createToken(String email) {
		Date exp = new Date(System.currentTimeMillis() + 1000*5); // 30분 만료
		return Jwts.builder().header().add("typ", "JWT").and()
				.claim("email", email).expiration(exp).signWith(secretKey).compact();
	}
	
	public Jws<Claims> validate (String token){
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
	}
	
	public String createRefreshToken(String email) {
		long currentTime = System.currentTimeMillis();
		JwtBuilder jwtRefreshTokenBuilder = Jwts.builder();
		
		jwtRefreshTokenBuilder.claim("email", email);
		jwtRefreshTokenBuilder.issuedAt(new Date(currentTime));
		jwtRefreshTokenBuilder.expiration(new Date(currentTime + refreshTokenExpireTime*1000));
		jwtRefreshTokenBuilder.signWith(secretKey);
		
		return jwtRefreshTokenBuilder.compact();
	}
	
	public String getEmail(String token) {
		Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
		return claims.get("email", String.class);
	}
	
	public boolean isTokenExpired(String token) {
		Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
		return claims.getExpiration().before(new Date());
	}
	
	public String createAccessTokenFromRefreshToken(String refreshToken) {
	    try {
	        // 리프레시 토큰 유효성 검사
	        validate(refreshToken);
	        // 리프레시 토큰에서 이메일 추출
	        String email = getEmail(refreshToken);
	        // 새로운 액세스 토큰 생성
	        return createToken(email);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to create access token from refresh token", e);
	    }
	}

	
}
