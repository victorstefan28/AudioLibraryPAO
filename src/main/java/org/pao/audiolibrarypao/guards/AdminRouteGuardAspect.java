package org.pao.audiolibrarypao.guards;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.pao.audiolibrarypao.entities.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminRouteGuardAspect {

    @Before("@annotation(RequiresAdmin)")
    public void checkAdminAuthorization(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !isAdmin(authentication.getPrincipal())) {
            throw new AccessDeniedException("Admin access required.");
        }
    }

    private boolean isAdmin(Object principal) {
        if (principal instanceof User) {
            return ((User) principal).isAdmin();
        }
        return false;
    }
}
