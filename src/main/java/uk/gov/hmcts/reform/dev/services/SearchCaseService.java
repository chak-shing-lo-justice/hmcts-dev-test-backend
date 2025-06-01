package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.enums.CaseStatus;
import uk.gov.hmcts.reform.dev.exceptions.cases.UnauthorizedException;
import uk.gov.hmcts.reform.dev.models.data.CaseData;
import uk.gov.hmcts.reform.dev.repositories.CaseRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service for searching and managing cases.
 * Provides methods to retrieve, search, and delete cases.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchCaseService {
    private final CaseRepository caseRepository;
    private final AuthorizationService authorizationService;

    public Optional<CaseData> getCaseById(long caseId) {
        if (authorizationService.hasAccess(caseId)) {
            return caseRepository.findById(caseId);
        } else {
            throw new UnauthorizedException();
        }
    }

    public List<CaseData> getAllCases() {
        // TODO implement pagination
        return filterCases(caseRepository.findAll());
    }

    public List<CaseData> searchCaseByTitle(String caseTitle) {
        if (caseTitle == null || caseTitle.isEmpty()) {
            return List.of();
        }
        return filterCases(caseRepository.findAllByTitleContainsIgnoreCase(caseTitle.trim()));
    }

    private List<CaseData> filterCases(List<CaseData> cases) {
        return cases.stream()
            .filter(caseData -> !CaseStatus.DELETED.equals(caseData.getStatus()))
            .filter(caseData -> authorizationService.hasAccess(caseData.getId()))
            .toList();
    }
}
