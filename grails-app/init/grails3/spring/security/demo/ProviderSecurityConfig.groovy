package grails3.spring.security.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

//@Configuration
@EnableWebSecurity
public class ProviderSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationProvider authenticationProvider

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/admin/*")
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider)
    }
}