package com.team2.fithub.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.team2.fithub.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	private final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}
		
		String token = request.getHeader(HEADER_AUTH);
		if(token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
			try {
				jwtUtil.validate(token);
				return true;
			} catch (Exception e) {
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("refreshToken")) {
							String refreshToken = cookie.getValue();
							try {
								jwtUtil.validate(refreshToken);
								String email = jwtUtil.getEmail(refreshToken);
								String newAccessToken = jwtUtil.createAccessTokenFromRefreshToken(refreshToken);
								
								response.setHeader(HEADER_AUTH, newAccessToken);
								return true;
							} catch (Exception ex) {
								response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
								response.getWriter().write("Invalid refresh token");
								return false;
							}
						}
					}
				}
			}
		}
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Unauthorized access");
		return false;
	}

}
