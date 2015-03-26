# Grails 3 Spring Security Demo

## Environment Setup

1. Set your Grails environment to use Grails 3. Currently, this demo is built using Grails 3.0.0.RC3.
2. Clone or fork the project

## Initial Steps

These initial steps are already done for the code that is on GitHub. I just wanted to note what was done to get the
code to this starting point.

* Opened Grails command line and run `create-app grails3-spring-security-demo --profile=web`
* Updated `grails-app/conf/logback.groovy` and added the following:

```groovy
    // added so that we get more of the Spring Boot startup
    logger('org.springframework', INFO)
```

* Opened Grails command line and run `create-controller grails3.spring.security.demo.Admin` to create a controller
   that we can test against. I also changed the `index()` method of the `AdminController` to render something to the 
   response
* I added the following entry to the `build.gradle` dependencies section:

```groovy
    compile "org.springframework.boot:spring-boot-starter-security"
```

* Added `@ComponentScan` to `grails-app/init/grails3/spring/security/demo/Application.groovy`


## Demos

So, if you run the application as-is from GitHub, you will have an application with a single controller. All URLs to 
the application are protected via a randomly generated password that you can find printed to the console during 
application startup. The default username is "user". To run the application, just go to 
`grails-app/init/grails3/spring/security/demo/Application.groovy` and run the class. Or, you can use gradle and 
execute `gradle run`

Remember between runs of the demos to clear your HTTP basic credentials. Most browsers support the ctrl+shift+del
shortcut to bring up the dialog to clear browser history/settings/credentials.

### In-memory authentication example

To enable the in-memory security configuration, go to 
`grails-app/init/grails3/spring/security/demo/InMemorySecurityConfig.groovy` and uncomment the `@Configuration`
annotation. At that point, you should be able to authenticate with the username/password listed in that source. You
should be able to access the application root page without needing to provide credentials, but if you navigate to
/admin, you should be challenged to authenticate.
 
### Basic provider authentication example

To enable the provider security configuration, first we need to comment the `@Configuration` in the in-memory 
authentication example. Then, go to 
`grails-app/init/grails3/spring/security/demo/ProviderSecurityConfig.groovy` and uncomment the `@Configuration`
annotation. Then we also need to go to
`src/main/groovy/grails3/spring/security/demo/SimpleAuthenticationProvider.groovy` and uncomment the `@Component`
annotation.
At that point, you should be able to authenticate with the username/password listed in that source. You
should be able to access the application root page without needing to provide credentials, but if you navigate to
/admin, you should be challenged to authenticate.

### URL Intercept with provider example

To enable the URL intercept security configuration, first we need to comment the `@Configuration` and ``@Component` 
annotations that we enabled in the provider authentication example. Then, go to 
`grails-app/init/grails3/spring/security/demo/InterceptMapSecurityConfig.groovy` and uncomment the `@Configuration`
annotation. Then we also need to go to
`grails-app/conf/spring/resources.groovy` and uncomment the authenticationProvider bean definition.
At that point, you should be able to authenticate with the username/password listed in that source. You
should be able to access the application root page without needing to provide credentials, but if you navigate to
/admin, you should be challenged to authenticate.