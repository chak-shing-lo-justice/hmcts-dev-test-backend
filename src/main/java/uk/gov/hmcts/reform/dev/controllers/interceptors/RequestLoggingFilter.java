package uk.gov.hmcts.reform.dev.controllers.interceptors;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.dev.services.RequestAuditLogService;

import java.io.IOException;

import static uk.gov.hmcts.reform.dev.services.RequestAuditLogService.REQUEST_END_FORMAT;
import static uk.gov.hmcts.reform.dev.services.RequestAuditLogService.REQUEST_START_FORMAT;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestLoggingFilter implements Filter {
    private final RequestAuditLogService requestAuditLogService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Log incoming request
        log.info(REQUEST_START_FORMAT, httpRequest.getMethod(), httpRequest.getRequestURI());

        // Proceed with the filter chain
        chain.doFilter(request, response);

        // Log outgoing response
        log.info(REQUEST_END_FORMAT, httpRequest.getMethod(), httpRequest.getRequestURI(),
                 httpResponse.getStatus());

        if (httpResponse.getStatus() == HttpStatus.OK.value()) {
            requestAuditLogService.logRequestAudit(httpRequest.getMethod(), httpRequest.getRequestURI(),
                                                   httpRequest.getRemoteAddr(),
                                                   HttpStatus.resolve(httpResponse.getStatus()),
                                                   "");
        }
    }
}
