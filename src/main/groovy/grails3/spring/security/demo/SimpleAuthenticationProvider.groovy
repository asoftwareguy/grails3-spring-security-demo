package grails3.spring.security.demo

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

//@Component
public class SimpleAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!authentication.principal) {
            throw new UsernameNotFoundException("Username was not provided.")
        }
        if (!authentication.credentials) {
            throw new BadCredentialsException(String.format("User [%s] password was not provided.", authentication.name))
        }

        String username = authentication.principal.toString()
        String password = authentication.credentials.toString()

        if (username != 'asoftwareguy') {
            throw new UsernameNotFoundException(String.format("Unable to look up user [%s].", username))
        }

        if (password != 'secret2') {
            throw new BadCredentialsException(String.format("User [%s] password was provided incorrect.", username))
        }

        new UsernamePasswordAuthenticationToken(username, password, [new SimpleGrantedAuthority("ROLE_ADMIN")])
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class
    }
}