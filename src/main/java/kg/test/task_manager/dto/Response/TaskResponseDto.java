package kg.test.task_manager.dto.Response;

import kg.test.task_manager.enums.TaskPriority;
import kg.test.task_manager.enums.TaskStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private boolean completed;
    private String assignee;
    private LocalDate dueDate;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
