package uk.gov.hmcts.reform.dev.exceptions.cases;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends HandledException {
    public UnauthorizedException() {
        super("Unauthorized access");
    }
}
