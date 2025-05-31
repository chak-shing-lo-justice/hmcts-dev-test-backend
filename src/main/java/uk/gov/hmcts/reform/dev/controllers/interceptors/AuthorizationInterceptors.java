package uk.gov.hmcts.reform.dev.controllers.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import uk.gov.hmcts.reform.dev.annotations.RequireLoginSession;
import uk.gov.hmcts.reform.dev.exceptions.cases.UnauthorizedException;
import uk.gov.hmcts.reform.dev.services.AuthService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationInterceptors implements HandlerInterceptor {
    private final AuthService authService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (requireLoginSession(handler)) {
            String authToken = request.getHeader("Authorization");
            if (!authService.isUserLoggedIn(authToken)) {
                throw new UnauthorizedException();
            }
            authService.setCurrentUser(authToken);
        }
        return true;
    }

    private boolean requireLoginSession(Object handler) {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethod().isAnnotationPresent(RequireLoginSession.class)
                || handlerMethod.getBean().getClass().isAnnotationPresent(RequireLoginSession.class);
        } catch (ClassCastException e) {
            return false;
        }
    }
}
