package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.data.CaseAccess;

@Repository
public interface CaseAccessRepository extends CrudRepository<CaseAccess, CaseAccess.CaseAccessId> {
    boolean existsByCaseIdAndUsername(Long caseId, String userName);
}
