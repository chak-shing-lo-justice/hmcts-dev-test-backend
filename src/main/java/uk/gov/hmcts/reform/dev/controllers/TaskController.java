package uk.gov.hmcts.reform.dev.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.annotations.RequireLoginSession;
import uk.gov.hmcts.reform.dev.exceptions.cases.IdNotFoundException;
import uk.gov.hmcts.reform.dev.models.data.TaskData;
import uk.gov.hmcts.reform.dev.services.SearchTaskService;
import uk.gov.hmcts.reform.dev.services.UpdateTaskService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for querying and managing tasks.
 * Provides endpoints to create, retrieve, search, and delete tasks.
 */
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequireLoginSession
public class TaskController {
    private final SearchTaskService searchTaskService;
    private final UpdateTaskService updateTaskService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public List<TaskData> getCaseById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return searchTaskService.getAll();
        } else {
            return List.of(searchTaskService.getById(id)
                .orElseThrow(IdNotFoundException::new));
        }
    }

    @PostMapping(produces = "application/json")
    public TaskData create(@RequestBody TaskData taskData) {
        TaskData caseCreated = updateTaskService.create(taskData);
        return caseCreated;
    }

    @PostMapping(value = "/{id}", produces = "application/json")
    public TaskData update(@PathVariable long id, @RequestBody TaskData taskData) {
        TaskData caseCreated = updateTaskService.update(id, taskData);
        return caseCreated;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<TaskData> search(@RequestParam String title) {
        List<TaskData> tasks = searchTaskService.searchByTitle(title);
        return tasks;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        updateTaskService.deleteById(id);
        return ok().build();
    }
}
