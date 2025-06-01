package uk.gov.hmcts.reform.dev.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.annotations.RequireLoginSession;
import uk.gov.hmcts.reform.dev.models.request.UserProfileRequest;
import uk.gov.hmcts.reform.dev.services.AuthenticationService;

import java.security.NoSuchAlgorithmException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final AuthenticationService authenticationService;

    // TODO assume using HTTPS
    @PostMapping("/login")
    public String login(@RequestBody UserProfileRequest request) throws NoSuchAlgorithmException {
        return authenticationService.login(request.getUsername(), request.getPassword());
    }

    @RequireLoginSession
    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        return (authenticationService.logout()) ? ok().build() : ResponseEntity.badRequest().build();
    }

    @RequireLoginSession
    @GetMapping("/verify")
    public ResponseEntity<Void> verifyLoginSession() {
        // session is verified by the interceptor already
        return ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserProfileRequest request) throws NoSuchAlgorithmException {
        authenticationService.createUserProfile(request.getUsername(), request.getPassword(), request.getRoles());
        return ok().build();
    }
}
