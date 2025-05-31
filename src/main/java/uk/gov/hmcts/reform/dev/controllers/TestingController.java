package uk.gov.hmcts.reform.dev.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.models.request.UserProfileRequest;
import uk.gov.hmcts.reform.dev.services.AuthService;

import java.security.NoSuchAlgorithmException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestingController {
    private final AuthService authService;

    // TODO assume using HTTPS
    @Validated
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserProfileRequest request) throws NoSuchAlgorithmException {
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String bearerToken =  request.getHeader("Authorization");
        return (authService.logout(bearerToken)) ? ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserProfileRequest request) throws NoSuchAlgorithmException {
        authService.createUserProfile(request.getUsername(), request.getPassword(), request.getRoles());
        return ok().build();
    }
}
