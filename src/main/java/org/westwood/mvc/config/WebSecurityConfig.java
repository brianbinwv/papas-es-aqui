package org.westwood.mvc.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.westwood.mvc.security.CustomAuthenticationProcessingFilter;
import org.westwood.mvc.security.CustomAuthenticationProvider;
import org.westwood.mvc.security.CustomUserDetailsService;

 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	
	private CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
	
	
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    
    
    @Bean
    public CustomAuthenticationProvider authenticationProvider() {
    	
    	if (customAuthenticationProvider == null) {
    		customAuthenticationProvider = new CustomAuthenticationProvider();
    	}
    	
    	if (customAuthenticationProvider.userDetailsServiceIsNull()) {
    		customAuthenticationProvider.setUserDetailsService(userDetailsService());
    	}
    	
    	return customAuthenticationProvider;
    }
 
    
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.httpBasic().disable()
    		.csrf().disable()
    		.formLogin().disable()
    		.logout().disable()
    		.authenticationProvider(authenticationProvider())
    		.addFilterBefore(getFilter(), UsernamePasswordAuthenticationFilter.class).authorizeRequests()
    		.antMatchers("/blogs").hasAuthority("ADMIN")
    		.anyRequest().permitAll();

    }

    
    private RequestMatcher getRequestMatchers() {
    	return new OrRequestMatcher(new AntPathRequestMatcher("/**"));
    }
    
    
    private Filter getFilter() throws Exception {
    	return new CustomAuthenticationProcessingFilter(getRequestMatchers(), authenticationManager());
    }
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }
    
    

    
     
}