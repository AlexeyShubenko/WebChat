package com.course.config;

import com.course.security.CustomAuthenticationProvider;
import com.course.security.CustomMethodSecurityExpressionHandler;
import com.course.security.MyAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

/**
 * Created by Владимир on 29.07.2017.
 */

@Configuration
@EnableWebSecurity
@ComponentScan("com.course.security")
public class AppSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

//    public AppSecurity(CustomAuthenticationProvider customAuthenticationProvider){
//        this.customAuthenticationProvider = customAuthenticationProvider;
//    }

//    public CustomAuthenticationProvider getAuthProvider(){
//
//        return new CustomAuthenticationProvider();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().addFilter(new MyAuthFilter( new RegexRequestMatcher("/", null)))
                .httpBasic();
    }



    @Bean
    public CustomMethodSecurityExpressionHandler getCustomHandler() {

        return new CustomMethodSecurityExpressionHandler();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }



    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
