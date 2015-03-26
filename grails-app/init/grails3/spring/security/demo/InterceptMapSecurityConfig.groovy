package grails3.spring.security.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

//@Configuration
@EnableWebSecurity
public class InterceptMapSecurityConfig extends WebSecurityConfigurerAdapter {

    // could we put this in resources.groovy?...
    @Bean(name = "securityConfig")
    ConfigObject securityConfig() {
        return new ConfigSlurper().parse(new ClassPathResource('spring-security-config.groovy').URL)
    }

    @Autowired
    ConfigObject securityConfig

    @Autowired
    AuthenticationProvider authenticationProvider

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

        Map<String, ArrayList<String>> interceptMap = securityConfig.grails.plugin.springsecurity.interceptUrlMap
        interceptMap.each { entry ->
            if (entry.value.any { it == 'permitAll' }) {
                http.authorizeRequests().antMatchers(entry.key).permitAll()
            } else if (entry.value.any { it == 'IS_AUTHENTICATED_FULLY' || it == 'isFullyAuthenticated()' }) {
                http.authorizeRequests().antMatchers(entry.key).fullyAuthenticated().and().httpBasic()
            } else if (entry.value.any { it == 'IS_AUTHENTICATED_REMEMBERED' }) {
                http.authorizeRequests().antMatchers(entry.key).rememberMe().and().httpBasic()
            } else if (entry.value.any { it == 'IS_AUTHENTICATED_ANONYMOUSLY' }) {
                http.authorizeRequests().antMatchers(entry.key).anonymous().and().httpBasic()
            } else if (entry.value.any { it.startsWith('ROLE_') }) {
                List<String> roles = entry.value.findAll { it.startsWith('ROLE_') }
                //Spring Security doesn't need the 'ROLE_'
                roles = roles.collect {it - 'ROLE_'}
                http.authorizeRequests().antMatchers(entry.key).hasAnyRole(roles as String[]).and().httpBasic()
            }
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider)
    }
}