package uk.gov.hmcts.reform.dev.exceptions.cases;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCaseException extends HandledException {
    public InvalidCaseException() {
        super("Invalid case data provided");
    }
}
