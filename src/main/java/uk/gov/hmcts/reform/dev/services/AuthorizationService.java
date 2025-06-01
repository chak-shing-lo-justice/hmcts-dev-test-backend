package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.data.CaseAccess;
import uk.gov.hmcts.reform.dev.repositories.CaseAccessRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationService {
    private final CaseAccessRepository caseAccessRepository;
    private final AuthenticationService authenticationService;

    public boolean hasAccess(Long caseId) {
        return caseAccessRepository.existsByCaseIdAndUsername(caseId,
                                                              authenticationService.getUserProfile().getUsername());
    }

    public void authoriseAccess(Long caseId) {
        caseAccessRepository.save(CaseAccess.builder()
                                      .caseId(caseId)
                                      .username(authenticationService.getUserProfile().getUsername())
                                      .build());
    }
}
