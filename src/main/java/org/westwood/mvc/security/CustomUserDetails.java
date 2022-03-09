package org.westwood.mvc.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.westwood.mvc.model.User;

public class CustomUserDetails implements UserDetails {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
	//private Collection<? extends GrantedAuthority> authorities = new Collection<? extends GrantedAuthority>();
	
	
	private User user;
     
    public CustomUserDetails(User user) {
        this.user = user;
    }
 
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//    	
//    }
    
    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
 
    public void setAuthorities() {
    	
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
     
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }
 
}

