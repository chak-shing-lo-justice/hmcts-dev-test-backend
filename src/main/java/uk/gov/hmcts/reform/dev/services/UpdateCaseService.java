package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.exceptions.cases.CaseNotFoundException;
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

    public CaseData createCase(CaseData caseData) {
        // TODO access control, validation, etc.
        CaseData caseDataCreated = caseRepository.save(caseData.toBuilder()
                                       .id(null)
                                       .createdDate(null)
                                       .lastUpdatedTime(null)
                                       .build());
        if (Math.random() < 0.5) {
            throw new RuntimeException(
                "Ops!! Math.random() < 0.5, Need to rollback. Case %d not created".formatted(caseDataCreated.getId()));
        }

        return caseDataCreated;
    }

    public int deleteCaseById(long caseId) {
        CaseData caseData = caseRepository.findById(caseId)
            .orElseThrow(CaseNotFoundException::new);

        caseRepository.delete(caseData);

        return 1;
    }
}
