package uk.gov.hmcts.reform.dev.models.data;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@IdClass(CaseAccess.CaseAccessId.class)
public class CaseAccess {
    @Id
    @JoinColumn(name = "case_data", referencedColumnName = "id")
    private Long caseId;

    @Id
    private String username;

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class CaseAccessId {
        private Long caseId;
        private String username;
    }
}
