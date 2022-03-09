package org.westwood.mvc.security;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	public CustomAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher, 
			AuthenticationManager authenticationManager) {
		super(requiresAuthenticationRequestMatcher);
		setAuthenticationManager(authenticationManager);
	}

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		// extract user name from request
		String header = request.getHeader("REMOTE_USER");
		
		// hard code for testing
		header = "john-smith@gmail.com";
		
		// create a token object to pass to authentication provider
		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(header, null);
		return getAuthenticationManager().authenticate(token);
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		// save user principle in security context
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}
	
}

