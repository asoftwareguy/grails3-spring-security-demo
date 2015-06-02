package grails3.spring.security.demo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

class SecurityTagLib {
    static namespace = "security"

    def name = { attrs ->
        out << SecurityContextHolder.context.authentication?.name
    }

    def ifLoggedIn = {attrs, body ->
        if(SecurityContextHolder.context.authentication?.isAuthenticated()) {
            out << body()
        }
    }

    def hasRole = { attrs, body ->

        def result = SecurityContextHolder.context.authentication?.authorities?.find { GrantedAuthority g ->
            g.authority == attrs.role
        }

        if(result) {
            out << body()
        }
    }

    def roles = { attrs ->
        out << SecurityContextHolder.context.authentication?.authorities
    }
}
