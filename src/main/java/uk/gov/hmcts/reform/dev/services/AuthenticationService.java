package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.beans.LocalDateTimeProvider;
import uk.gov.hmcts.reform.dev.exceptions.cases.UnauthorizedException;
import uk.gov.hmcts.reform.dev.exceptions.cases.UserNotFoundException;
import uk.gov.hmcts.reform.dev.models.data.UserProfile;
import uk.gov.hmcts.reform.dev.repositories.UserProfileRepository;
import uk.gov.hmcts.reform.dev.utils.SecurityUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A cost-free service for handling user authentication and authorization.
 * Provides methods for maintaining user profiles, logging in, logging out,
 * and checking login status
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {
    private static final ThreadLocal<UserProfile> USER_PROFILE = new ThreadLocal<>();
    private final LocalDateTimeProvider time;
    private final UserProfileRepository userProfileRepository;

    public UserProfile createUserProfile(String userName, String password, List<String> roles)
            throws NoSuchAlgorithmException {
        byte[] verySecureSalt = SecurityUtil.generateSalt();

        return userProfileRepository.save(UserProfile.builder()
                                              .username(userName)
                                              .passwordHash(SecurityUtil.hashPassword(password, verySecureSalt))
                                              .salt(verySecureSalt)
                                              .roles(roles)
                                              .build());
    }

    public String login(String userName, String password) throws NoSuchAlgorithmException {
        UserProfile userProfile = userProfileRepository.findById(userName)
            .orElseThrow(UserNotFoundException::new);

        if (Arrays.equals(userProfile.getPasswordHash(),
                          SecurityUtil.hashPassword(password, userProfile.getSalt()))) {
            // TODO: implement proper token generation
            String token = "a-bear-token-not-bearer-dont-get-confuse" + time.now().toString();
            userProfile.setToken(token);
            userProfileRepository.save(userProfile);
            return token;
        } else {
            throw new UnauthorizedException();
        }
    }

    public boolean logout() {
        UserProfile userProfile = USER_PROFILE.get();
        userProfile.setToken(null);
        userProfileRepository.save(userProfile);
        return true;
    }

    public boolean authenticateUserToken(String bearerToken) {
        Optional<UserProfile> userProfileOptional = Optional.ofNullable(bearerToken)
            .flatMap(this::getUserProfileByToken);

        if (userProfileOptional.isPresent()) {
            USER_PROFILE.set(userProfileOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public UserProfile getUserProfile() {
        return USER_PROFILE.get();
    }

    @Cacheable(cacheManager = "requestScopedCacheManager", cacheNames = "getUserProfileByToken")
    public Optional<UserProfile> getUserProfileByToken(String bearerToken) {
        return userProfileRepository.findByToken(bearerToken.replace("Bearer ", ""));
    }
}
