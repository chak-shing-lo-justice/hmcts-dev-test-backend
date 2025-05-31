package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Optional<CaseData> getCaseById(int caseId) {
        return caseRepository.findById(caseId);
    }

    public List<CaseData> getAllCases() {
        // TODO implement pagination
        return caseRepository.findAll();
    }

    public List<CaseData> searchCaseByTitle(String caseTitle) {
        if (caseTitle == null || caseTitle.isEmpty()) {
            return List.of();
        }
        return caseRepository.findAllByTitleContainsIgnoreCase(caseTitle.trim());
    }
}
