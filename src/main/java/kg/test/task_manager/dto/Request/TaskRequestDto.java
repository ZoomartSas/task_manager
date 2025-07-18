package kg.test.task_manager.dto.Request;

import kg.test.task_manager.enums.TaskPriority;
import kg.test.task_manager.enums.TaskStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDto {
    private String title;
    private String description;
    private TaskStatus status;
    private boolean completed;
    private String assignee;
    private LocalDate dueDate;
    private String recipientEmail;
    private TaskPriority priority;
}

