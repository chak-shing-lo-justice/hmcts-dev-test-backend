package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.data.TaskData;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<TaskData, Integer> {

    Optional<TaskData> findById(Long id);

    @Override
    List<TaskData> findAll();

    List<TaskData> findAllByTitleContainsIgnoreCase(String title);
}
