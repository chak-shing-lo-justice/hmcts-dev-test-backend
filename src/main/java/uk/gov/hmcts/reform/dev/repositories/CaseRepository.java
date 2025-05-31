package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.data.CaseData;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends CrudRepository<CaseData, Integer> {

    Optional<CaseData> findById(Long id);

    @Override
    List<CaseData> findAll();

    List<CaseData> findAllByTitleContainsIgnoreCase(String title);
}
