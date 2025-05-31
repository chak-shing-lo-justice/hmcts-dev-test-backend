package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.data.RequestAuditLog;

@Repository
public interface RequestAuditLogRepository extends CrudRepository<RequestAuditLog, Long> {
}
