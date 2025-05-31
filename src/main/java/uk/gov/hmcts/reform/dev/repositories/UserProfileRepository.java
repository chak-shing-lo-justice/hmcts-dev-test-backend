package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.data.UserProfile;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, String> {
    Optional<UserProfile> findByToken(String token);
}
