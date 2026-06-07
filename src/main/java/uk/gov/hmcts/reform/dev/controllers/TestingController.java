package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.enums.TaskStatus;
import uk.gov.hmcts.reform.dev.models.data.TaskData;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/testing")
public class TestingController {
    @GetMapping(value = "/get-example-task", produces = "application/json")
    public ResponseEntity<TaskData> getExampleTask() {
        return ok(new TaskData(1L, "Task Title", "Task Description", TaskStatus.OPEN,
                               LocalDateTime.now().plusDays(5), LocalDateTime.now(), LocalDateTime.now()
        ));
    }
}
