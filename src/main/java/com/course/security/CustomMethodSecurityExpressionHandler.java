package com.course.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;

/**
 * Created by Владимир on 29.07.2017.
 */
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {


    // parent constructor
    public CustomMethodSecurityExpressionHandler() {
        super();
    }

    @Override
    public StandardEvaluationContext createEvaluationContextInternal(Authentication auth, MethodInvocation mi) {
        // due to private methods, call original method, then override it's root with ours
        StandardEvaluationContext ctx = (StandardEvaluationContext) super.createEvaluationContext(auth, mi);
        ctx.setRootObject( new CustomMethodSecurityExpressionRoot(auth) );
        return ctx;
    }



}
