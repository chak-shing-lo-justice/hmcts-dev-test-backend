package uk.gov.hmcts.reform.dev.exceptions.cases;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class CaseNotFoundException extends HandledException {
    public CaseNotFoundException() {
        super("Case not found");
    }
}
