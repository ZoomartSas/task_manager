package kg.test.task_manager.repository;

import jakarta.annotation.Priority;
import kg.test.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query(value = "UPDATE tasks SET title = :title, " +
                                    "description = :description, " +
                                    "status = :status, " +
                                    "completed = :completed," +
                                    "assignee = :assignee," +
                                    "due_date = :dueDate,  " +
                                    "priority = :priority   " +
                    "WHERE id = :id", nativeQuery = true)
    int updateTaskNative(@Param("id") Long id,
                         @Param("title") String title,
                         @Param("description") String description,
                         @Param("status") String status,
                         @Param("completed") boolean completed,
                         @Param("assignee") String assignee,
                         @Param("dueDate") LocalDate dueDate,
                         @Param("priority") String priority);
}