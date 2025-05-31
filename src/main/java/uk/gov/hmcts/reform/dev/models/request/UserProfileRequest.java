package uk.gov.hmcts.reform.dev.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private List<String> roles;
}
