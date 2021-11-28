package com.krazymood.app.configuration;

import com.krazymood.app.entities.CategoryList;
import com.krazymood.app.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired BCryptPasswordEncoder passwordEncoder;
	@Autowired ContentService contentService;

	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("ksuraj1002")
                .password(passwordEncoder.encode("krazysuraj"))
                .roles("ADMIN");
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/**","/js/**", "/css/**").permitAll()
		.and().formLogin()
		.defaultSuccessUrl("/admin")
		.and()
		.exceptionHandling().accessDeniedPage("/failure");
		
		http.csrf().disable();
		http.headers().frameOptions().disable();

	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CategoryList getCategoryList(){
		Map<String,Object> categoryMap= contentService.getCategoryList();
		return new CategoryList(categoryMap);
	}

}
