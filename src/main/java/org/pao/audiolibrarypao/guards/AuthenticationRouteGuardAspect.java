package org.pao.audiolibrarypao.guards;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationRouteGuardAspect {

    @Before("@annotation(RequiresAuthentication)")
    public void checkAuthentication(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)

        {
            throw new AuthenticationCredentialsNotFoundException("Authentication required.");
        }

    }
}
