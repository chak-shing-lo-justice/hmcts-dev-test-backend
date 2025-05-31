package uk.gov.hmcts.reform.dev.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CaseStatus {
    // TODO support i18n labeling?
    @JsonProperty("Open case")
    OPEN,
    @JsonProperty("Closed case")
    CLOSED,
    @JsonProperty("Deleted case")
    DELETED
}
