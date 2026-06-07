package uk.gov.hmcts.reform.dev.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskStatus {
    // TODO support i18n labeling?
    @JsonProperty("Opened")
    OPEN,
    @JsonProperty("Closed")
    CLOSED,
    @JsonProperty("Deleted")
    DELETED
}
