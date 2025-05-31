package uk.gov.hmcts.reform.dev.exceptions.cases;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class HandledException extends RuntimeException {

    public HandledException() {
        super("Internal server error");
    }

    public HandledException(String msg) {
        super(msg);
    }
}
