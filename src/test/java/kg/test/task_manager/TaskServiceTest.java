package kg.test.task_manager;

import kg.test.task_manager.dto.Request.TaskRequestDto;
import kg.test.task_manager.dto.Response.TaskResponseDto;
import kg.test.task_manager.entity.Task;
import kg.test.task_manager.enums.TaskPriority;
import kg.test.task_manager.enums.TaskStatus;
import kg.test.task_manager.mapper.TaskMapper;
import kg.test.task_manager.repository.TaskRepository;

import kg.test.task_manager.service.EmailService;
import kg.test.task_manager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TaskService service;

    private TaskRequestDto requestDto;
    private Task task;
    private TaskResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = TaskRequestDto.builder()
                .title("Test Task")
                .description("Description")
                .status(TaskStatus.IN_PROGRESS)
                .completed(false)
                .assignee("john.doe@example.com")
                .dueDate(LocalDate.now().plusDays(3))
                .priority(TaskPriority.HIGH)
                .build();

        task = Task.builder()
                .id(1L)
                .title("Test Task")
                .description("Description")
                .status(TaskStatus.IN_PROGRESS)
                .completed(false)
                .assignee("john.doe@example.com")
                .dueDate(LocalDate.now().plusDays(3))
                .priority(TaskPriority.HIGH)
                .build();

        responseDto = TaskResponseDto.builder()
                .id(1L)
                .title("Test Task")
                .description("Description")
                .status(TaskStatus.IN_PROGRESS)
                .completed(false)
                .assignee("john.doe@example.com")
                .dueDate(LocalDate.now().plusDays(3))
                .priority(TaskPriority.HIGH)
                .build();
    }

    @Test
    void create_shouldSaveAndSendEmailAndReturnDto() {
        when(mapper.toEntity(requestDto)).thenReturn(task);
        when(repository.save(task)).thenReturn(task);
        when(mapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = service.create(requestDto);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());

        verify(repository).save(task);
        verify(emailService).sendTaskCreatedEmail(task.getTitle(), task.getAssignee(), task.getDescription());
        verify(mapper).toDto(task);
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        List<Task> tasks = List.of(task);
        when(repository.findAll()).thenReturn(tasks);
        when(mapper.toDto(task)).thenReturn(responseDto);

        List<TaskResponseDto> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void getById_shouldReturnDto_whenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(mapper.toDto(task)).thenReturn(responseDto);

        Optional<TaskResponseDto> result = service.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
    }

    @Test
    void getById_shouldReturnEmpty_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<TaskResponseDto> result = service.getById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void update_shouldModifyAndReturnDto_whenFound() {
        when(repository.updateTaskNative(
                eq(1L),
                eq(requestDto.getTitle()),
                eq(requestDto.getDescription()),
                eq(requestDto.getStatus().name()),
                eq(requestDto.isCompleted()),
                eq(requestDto.getAssignee()),
                eq(requestDto.getDueDate()),
                eq(requestDto.getPriority().name())
        )).thenReturn(1);

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(mapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = service.update(1L, requestDto);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        when(repository.updateTaskNative(
                anyLong(), any(), any(), any(), anyBoolean(), any(), any(), any()
        )).thenReturn(0);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.update(1L, requestDto));

        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}
