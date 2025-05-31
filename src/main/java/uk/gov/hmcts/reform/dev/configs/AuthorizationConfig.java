package uk.gov.hmcts.reform.dev.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uk.gov.hmcts.reform.dev.controllers.interceptors.AuthorizationInterceptors;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptors authorizationInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptors).addPathPatterns("/*");
    }
}
