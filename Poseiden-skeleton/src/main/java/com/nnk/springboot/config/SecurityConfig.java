package com.nnk.springboot.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasAnyAuthority("USER", "ADMIN")
			.antMatchers("/user/**").permitAll()
			.and()
			.formLogin()
			.defaultSuccessUrl("/bidList/list")
			.and()
			.logout()
			.logoutUrl("/app-logout")
			.logoutSuccessUrl("/")
			.permitAll();

	}
	
	
	@Override
	public void configure(WebSecurity web) {
		
		web.ignoring().antMatchers("/resources/**");
		
	}
		
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

}
