package org.westwood.mvc.security;


import java.util.*;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;



public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private UserDetailsService userDetailsService;
	public void setUserDetailsService(UserDetailsService s) {
		userDetailsService = s;
	}
	
	
	public boolean userDetailsServiceIsNull() {
		if (userDetailsService == null)
			return true;
		
		return false;
	}
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		boolean bAuthenticated = authentication.isAuthenticated();

		if (authentication instanceof PreAuthenticatedAuthenticationToken & !bAuthenticated) {
			PreAuthenticatedAuthenticationToken preAuth = (PreAuthenticatedAuthenticationToken)authentication;
			
			
			UserDetails user = userDetailsService.loadUserByUsername(preAuth.getName());
			
			
			if (user.getUsername().equals(preAuth.getName())) {
				preAuth.setDetails(user);
			}
			
			// TODO: get list of granted authorities from DB...hard code for now
			List<SimpleGrantedAuthority> sg = new ArrayList<SimpleGrantedAuthority>();
			sg.add(new SimpleGrantedAuthority("REGULAR_USER"));

			preAuth = new PreAuthenticatedAuthenticationToken(authentication.getName(), preAuth.getCredentials(), sg);
			bAuthenticated = true;

			preAuth.setAuthenticated(bAuthenticated);
			authentication = preAuth;
		}
		
		return authentication;
	}
	
	
	
//	private Authentication getValidationToken(String customToken) {
//	
//		// call custom logic
//		if (userDetailsService == null) {
//			System.out.println("CustomAuthenticationProvider.authenticate(): userDetails is null");
//			throw new BadCredentialsException("Authentication failed");
//		}
//
//		UserDetails user = userDetailsService.loadUserByUsername(customToken);
//
//		if (user.getUsername().equals(customToken)) {
//			System.out.println("1) CustomAuthenticationProvider: Authorities:");
//	    	for (GrantedAuthority g : user.getAuthorities()) {
//	    		System.out.println(g.getAuthority());
//	    	}
//			
//			
////			PreAuthenticatedAuthenticationToken userToken = new PreAuthenticatedAuthenticationToken("AuthenticatedUser", 
////					user.getAuthorities());
//
//	    	PreAuthenticatedAuthenticationToken userToken = new PreAuthenticatedAuthenticationToken(user.getUsername(), 
//					user.getAuthorities());
//	    
//			userToken.setDetails(user);
//			userToken.setAuthenticated(true);
//			
//			System.out.println("2) CustomAuthenticationProvider: Authorities:");
//	    	for (GrantedAuthority g : userToken.getAuthorities()) {
//	    		System.out.println(g.getAuthority());
//	    	}
//			
//			//SecurityContextHolder.getContext().setAuthentication(userToken);
//			
//			return userToken;			
//		}
//		else {
//			throw new AccessDeniedException("Invalid authenticationtoken");
//		}
//	}
	
	
	@Override
	public boolean supports(Class<?> authentication) {
		// use in-built class for simplicity
		return PreAuthenticatedAuthenticationToken.class.equals(authentication);
		//return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
}



