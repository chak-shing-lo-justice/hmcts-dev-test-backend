package uk.gov.hmcts.reform.dev.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to indicate that a login session is required for the annotated method or class.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLoginSession {
}
