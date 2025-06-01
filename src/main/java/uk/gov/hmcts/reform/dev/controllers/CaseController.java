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
import uk.gov.hmcts.reform.dev.exceptions.cases.CaseNotFoundException;
import uk.gov.hmcts.reform.dev.models.data.CaseData;
import uk.gov.hmcts.reform.dev.services.SearchCaseService;
import uk.gov.hmcts.reform.dev.services.UpdateCaseService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for querying and managing cases.
 * Provides endpoints to create, retrieve, search, and delete cases.
 */
@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequireLoginSession
public class CaseController {
    private final SearchCaseService searchCaseService;
    private final UpdateCaseService updateCaseService;

    @GetMapping(value = "/{caseId}", produces = "application/json")
    public List<CaseData> getCaseById(@PathVariable Integer caseId) {
        if (caseId == null || caseId <= 0) {
            return searchCaseService.getAllCases();
        } else {
            return List.of(searchCaseService.getCaseById(caseId)
                .orElseThrow(CaseNotFoundException::new));
        }
    }

    @PostMapping(produces = "application/json")
    public CaseData createCase(@RequestBody CaseData caseData) {
        CaseData caseCreated = updateCaseService.createCase(caseData);
        return caseCreated;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<CaseData> searchCase(@RequestParam String title) {
        List<CaseData> cases = searchCaseService.searchCaseByTitle(title);
        return cases;
    }

    @DeleteMapping(value = "/{caseId}", produces = "application/json")
    public ResponseEntity<Void> deleteCase(@PathVariable Long caseId) {
        updateCaseService.deleteCaseById(caseId);
        return ok().build();
    }
}
