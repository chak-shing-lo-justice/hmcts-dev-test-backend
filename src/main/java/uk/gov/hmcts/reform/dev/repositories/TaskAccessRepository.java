package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.data.TaskAccess;

@Repository
public interface TaskAccessRepository extends CrudRepository<TaskAccess, TaskAccess.TaskAccessId> {
    boolean existsByTaskIdAndUsername(Long taskId, String userName);
}
