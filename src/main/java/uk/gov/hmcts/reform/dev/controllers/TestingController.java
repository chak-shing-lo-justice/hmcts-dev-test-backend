package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.enums.CaseStatus;
import uk.gov.hmcts.reform.dev.models.data.CaseData;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/testing")
public class TestingController {
    @GetMapping(value = "/get-example-case", produces = "application/json")
    public ResponseEntity<CaseData> getExampleCase() {
        return ok(new CaseData(1L, "ABC12345", "Case Title",
                               "Case Description", CaseStatus.OPEN,
                               LocalDateTime.now(), LocalDateTime.now()
        ));
    }
}
