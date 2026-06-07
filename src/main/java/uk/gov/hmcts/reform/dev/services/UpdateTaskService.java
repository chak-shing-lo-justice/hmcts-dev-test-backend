package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.enums.TaskStatus;
import uk.gov.hmcts.reform.dev.exceptions.cases.IdNotFoundException;
import uk.gov.hmcts.reform.dev.exceptions.cases.UnauthorizedException;
import uk.gov.hmcts.reform.dev.models.data.TaskData;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.Optional;

/**
 * Service for creating, updating or deleting a case.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UpdateTaskService {
    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;

    public TaskData create(TaskData taskData) {
        // TODO access control, validation, etc.
        TaskData taskDataCreated = taskRepository.save(taskData.toBuilder()
                                       .id(null)
                                       .status(TaskStatus.OPEN)
                                       .createdDate(null)
                                       .lastUpdatedTime(null)
                                       .build());
        authorizationService.authoriseAccess(taskDataCreated.getId());

        return taskDataCreated;
    }

    public TaskData update(long id, TaskData taskData) {
        // TODO validation, etc.
        Optional<TaskData> existingData = taskRepository.findById(id);
        return taskRepository.save(existingData.orElseThrow(IdNotFoundException::new).toBuilder()
                                       .title(taskData.getTitle())
                                       .description(taskData.getDescription())
                                       .status(taskData.getStatus() == null
                                                   ? existingData.get().getStatus() : taskData.getStatus())
                                       .build());
    }

    public void deleteById(long taskId) {
        if (authorizationService.hasAccess(taskId)) {
            TaskData taskData = taskRepository.findById(taskId).orElseThrow(IdNotFoundException::new);

            taskData.setStatus(TaskStatus.DELETED);
            taskRepository.save(taskData);
        } else {
            throw new UnauthorizedException();
        }
    }
}
