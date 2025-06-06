package uk.gov.hmcts.reform.dev.controllers.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import uk.gov.hmcts.reform.dev.exceptions.cases.HandledException;
import uk.gov.hmcts.reform.dev.services.RequestAuditLogService;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static uk.gov.hmcts.reform.dev.services.RequestAuditLogService.REQUEST_ERROR_FORMAT;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExceptionLoggingAdvice {
    private final RequestAuditLogService requestAuditLogService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> logAllExceptions(Exception ex, HttpServletRequest request) throws Exception {
        log.error(REQUEST_ERROR_FORMAT, request.getMethod(), request.getRequestURI(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String details = ex.getMessage();
        if (ex instanceof HandledException) {
            ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
            status = responseStatus.value();
            if (isEmpty(ex.getMessage())) {
                details = ex.getClass().getSimpleName();
            }
        }

        requestAuditLogService.logRequestAudit(request.getMethod(), request.getRequestURI(), request.getRemoteAddr(),
                                               status, details);
        return ResponseEntity.status(status).build();
    }
}
