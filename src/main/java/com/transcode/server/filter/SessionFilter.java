package com.transcode.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


public class SessionFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			response.addHeader("Access-Control-Allow-Origin", "*");
			 response.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
			filterChain.doFilter(request, response);
		}
}
