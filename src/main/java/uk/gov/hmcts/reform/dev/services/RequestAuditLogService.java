package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.beans.LocalDateTimeProvider;
import uk.gov.hmcts.reform.dev.models.data.RequestAuditLog;
import uk.gov.hmcts.reform.dev.repositories.RequestAuditLogRepository;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestAuditLogService {
    public static final String REQUEST_START_FORMAT = "[Endpoint={}} {}}] Start";
    public static final String REQUEST_END_FORMAT = "[Endpoint={} {}] End. Status={}";
    public static final String REQUEST_ERROR_FORMAT = "[Endpoint={} {}}] Error occurred!";

    private final LocalDateTimeProvider time;
    private final RequestAuditLogRepository requestAuditLogRepository;


    public RequestAuditLog logRequestAudit(String httpMethod, String httpUri, String ipAddress, HttpStatus status,
                                           String details) {
        return logRequestAudit(httpMethod + " " + httpUri, ipAddress, status, details);
    }


    public RequestAuditLog logRequestAudit(String event, String ipAddress, HttpStatus status, String details) {
        return requestAuditLogRepository.save(RequestAuditLog.builder()
                                                  .event(event)
                                                  .ipAddress(ipAddress)
                                                  .timestamp(time.now())
                                                  .status(status.value())
                                                  .details(details)
                                                  .build());
    }
}
