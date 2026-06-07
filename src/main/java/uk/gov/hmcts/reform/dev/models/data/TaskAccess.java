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
@IdClass(TaskAccess.TaskAccessId.class)
public class TaskAccess {
    @Id
    @JoinColumn(name = "task_data", referencedColumnName = "id")
    private Long taskId;

    @Id
    private String username;

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class TaskAccessId {
        private Long taskId;
        private String username;
    }
}
