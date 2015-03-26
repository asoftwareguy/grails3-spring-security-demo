grails.plugin.springsecurity.interceptUrlMap = [
        '/':                  ['permitAll'],
        '/admin/*':           ['ROLE_ADMIN']
]