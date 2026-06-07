package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.enums.TaskStatus;
import uk.gov.hmcts.reform.dev.exceptions.cases.UnauthorizedException;
import uk.gov.hmcts.reform.dev.models.data.TaskData;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service for searching and managing cases.
 * Provides methods to retrieve, search, and delete cases.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchTaskService {
    private final TaskRepository taskRepository;
    private final AuthorizationService authorizationService;

    public Optional<TaskData> getById(long caseId) {
        if (authorizationService.hasAccess(caseId)) {
            return taskRepository.findById(caseId);
        } else {
            throw new UnauthorizedException();
        }
    }

    public List<TaskData> getAll() {
        // TODO implement pagination
        return filterValid(taskRepository.findAll());
    }

    public List<TaskData> searchByTitle(String caseTitle) {
        return filterValid(
            (caseTitle == null || caseTitle.isEmpty())
                ? taskRepository.findAll()
                : taskRepository.findAllByTitleContainsIgnoreCase(caseTitle.trim()));
    }

    private List<TaskData> filterValid(List<TaskData> cases) {
        return cases.stream()
            .filter(caseData -> !TaskStatus.DELETED.equals(caseData.getStatus()))
            .filter(caseData -> authorizationService.hasAccess(caseData.getId()))
            .toList();
    }
}
