package com.course.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * Created by Владимир on 29.07.2017.
 */
//зарегестрировали CustomMethodSecurityExpressionRoot
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    // parent constructor
    public CustomMethodSecurityExpressionHandler() {
        super();
    }

//    @Override
//    public StandardEvaluationContext createEvaluationContextInternal(Authentication auth, MethodInvocation mi) {
//        // due to private methods, call original method, then override it's root with ours
//        StandardEvaluationContext ctx = (StandardEvaluationContext) super.createEvaluationContext(auth, mi);
//        ctx.setRootObject( new CustomMethodSecurityExpressionRoot(auth) );
//        return ctx;
//    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication auth, MethodInvocation mi) {
        CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(auth);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }

}
