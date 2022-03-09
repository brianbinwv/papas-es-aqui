package org.westwood.mvc.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.westwood.mvc.model.User; 

public class CustomUserDetailsService implements UserDetailsService {
 
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	// TODO: retrieve user from the DB
    	// hard code one for now
    	User user = new User();
    	
    	user.setEmail("john-smith@gmail.com");
    	user.setFirstName("John");
    	user.setLastName("Smith");
    	user.setId(1L);
    	user.setPassword("fake password");
    	

    	// TODO: logic to extract available roles during the user lookup
    	CustomUserDetails c = new CustomUserDetails(user);
    	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority("REGULAR_USER"));
    	
    	c.getAuthorities().addAll(authorities);
    	return c;
    }
 
}