package com.course.config;

import com.course.security.CustomPermissionEvaluator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Created by Владимир on 29.07.2017.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomPermissionConfig extends GlobalMethodSecurityConfiguration  {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {

        DefaultMethodSecurityExpressionHandler defMethSecureExpHandler = new DefaultMethodSecurityExpressionHandler();
        defMethSecureExpHandler.setPermissionEvaluator(new CustomPermissionEvaluator());

        return super.createExpressionHandler();
    }

}