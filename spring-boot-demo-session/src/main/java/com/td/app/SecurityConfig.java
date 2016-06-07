package com.td.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**", "/app/**", "/lib/**", "/open/**","/oauth/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RequestMatcher csrfRequestMatcher = new RequestMatcher() {
			private AntPathRequestMatcher[] requestMatchers = { new AntPathRequestMatcher("/"), new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/logout") };

			@Override
			public boolean matches(HttpServletRequest request) {
				return false;
			}
		};
		http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and().authorizeRequests().antMatchers("/register").anonymous().antMatchers("/rest/auth/**").anonymous()
			.antMatchers("/", "/views/**", "/pages/**").hasAuthority("USER").anyRequest().fullyAuthenticated().and().formLogin().loginPage("/login").failureUrl("/login")
			.permitAll().and().logout().logoutUrl("/logout").deleteCookies("remember-me").logoutSuccessUrl("/login").permitAll().and().rememberMe();
	}

	@Bean
	public UserDetailsService userDetailsService() {

		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				String pass = new Md5PasswordEncoder().encodePassword("test", "");
				return new User(username, pass, AuthorityUtils.createAuthorityList("USER"));
			}
		};
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(new Md5PasswordEncoder());
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
}
