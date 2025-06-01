package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.enums.CaseStatus;
import uk.gov.hmcts.reform.dev.exceptions.cases.CaseNotFoundException;
import uk.gov.hmcts.reform.dev.exceptions.cases.UnauthorizedException;
import uk.gov.hmcts.reform.dev.models.data.CaseData;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;

/**
 * Service for creating, updating or deleting a case.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class UpdateCaseService {
    private final CaseRepository caseRepository;
    private final AuthorizationService authorizationService;

    public CaseData createCase(CaseData caseData) {
        // TODO access control, validation, etc.
        CaseData caseDataCreated = caseRepository.save(caseData.toBuilder()
                                       .id(null)
                                       .createdDate(null)
                                       .lastUpdatedTime(null)
                                       .build());
        authorizationService.authoriseAccess(caseDataCreated.getId());

        if (Math.random() < 0.5) {
            throw new RuntimeException(
                "Ops!! Math.random() < 0.5, Need to rollback. Case %d not created".formatted(caseDataCreated.getId()));
        }

        return caseDataCreated;
    }

    public void deleteCaseById(long caseId) {
        if (authorizationService.hasAccess(caseId)) {
            CaseData caseData = caseRepository.findById(caseId).orElseThrow(CaseNotFoundException::new);

            caseData.setStatus(CaseStatus.DELETED);
            caseRepository.save(caseData);
        } else {
            throw new UnauthorizedException();
        }
    }
}
