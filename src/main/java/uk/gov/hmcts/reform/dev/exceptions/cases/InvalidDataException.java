package uk.gov.hmcts.reform.dev.exceptions.cases;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends HandledException {
    public InvalidDataException() {
        super("Invalid data provided");
    }
}
