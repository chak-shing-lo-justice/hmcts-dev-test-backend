package uk.gov.hmcts.reform.dev.models.data;

import java.time.LocalDateTime;

public interface TransactionalAware {
    void setCreatedDate(LocalDateTime createdDate);

    LocalDateTime getCreatedDate();

    void setLastUpdatedTime(LocalDateTime lastUpdatedTime);

    LocalDateTime getLastUpdatedTime();
}
