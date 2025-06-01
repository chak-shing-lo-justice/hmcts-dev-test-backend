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
import uk.gov.hmcts.reform.dev.services.AuthenticationService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final AuthenticationService authenticationService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (requireLoginSession(handler)) {
            String authToken = request.getHeader("Authorization");
            if (!authenticationService.authenticateUserToken(authToken)) {
                throw new UnauthorizedException();
            }
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
