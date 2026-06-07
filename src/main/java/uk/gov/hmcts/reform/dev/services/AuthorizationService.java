package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.data.TaskAccess;
import uk.gov.hmcts.reform.dev.repositories.TaskAccessRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationService {
    private final TaskAccessRepository taskAccessRepository;
    private final AuthenticationService authenticationService;

    public boolean hasAccess(Long caseId) {
        return taskAccessRepository.existsByTaskIdAndUsername(caseId,
                                                              authenticationService.getUserProfile().getUsername());
    }

    public void authoriseAccess(Long taskId) {
        taskAccessRepository.save(TaskAccess.builder()
                                      .taskId(taskId)
                                      .username(authenticationService.getUserProfile().getUsername())
                                      .build());
    }
}
