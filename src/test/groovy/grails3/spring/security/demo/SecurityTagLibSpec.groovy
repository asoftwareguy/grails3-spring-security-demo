package grails3.spring.security.demo

import grails.test.mixin.TestFor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

@TestFor(SecurityTagLib)
class SecurityTagLibSpec extends Specification {

    void "test ifLoggedIn when a user is not logged in"() {
        when: "a user is not logged in"
        String result = applyTemplate('<security:ifLoggedIn>I am not logged in.</security:ifLoggedIn>')

        then: "ifLoggedIn does not print body since user is not logged in"
        result == ''
    }

    void "test ifLoggedIn when a user is logged in"() {
        when: "a user is logged in"
        SecurityContextHolder.context.setAuthentication(createAuthentication('user', 'password', 'ROLE_USER'))
        String result = applyTemplate('<security:ifLoggedIn>I am logged in.</security:ifLoggedIn>')

        then:
        result == 'I am logged in.'
    }

    static Authentication createAuthentication(username, password, roleString) {
        return new UsernamePasswordAuthenticationToken(username, password, [new SimpleGrantedAuthority(roleString)])
    }
}
