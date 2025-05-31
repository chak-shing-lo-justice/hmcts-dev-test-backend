package uk.gov.hmcts.reform.dev.beans;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
