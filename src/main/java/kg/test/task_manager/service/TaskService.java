package kg.test.task_manager.service;

import kg.test.task_manager.dto.Request.TaskRequestDto;
import kg.test.task_manager.dto.Response.TaskResponseDto;
import kg.test.task_manager.entity.Task;
import kg.test.task_manager.mapper.TaskMapper;
import kg.test.task_manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    private final TaskMapper mapper;

    private final EmailService emailService;

    public TaskResponseDto create(TaskRequestDto dto) {
        Task task = mapper.toEntity(dto);
        Task saved = repository.save(task);
        emailService.sendTaskCreatedEmail("recipient@example.com", "Новая задача", "Создана задача: " + saved.getTitle());
        return mapper.toDto(saved);
    }


    @Cacheable(value ="tasks")
    public List<TaskResponseDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    public Optional<TaskResponseDto> getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Transactional
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskResponseDto update(Long id, TaskRequestDto dto) {
        int updatedRows = repository.updateTaskNative(
                id,
                dto.getTitle(),
                dto.getDescription(),
                dto.getStatus().name(),
                dto.isCompleted(),
                dto.getAssignee(),
                dto.getDueDate(),
                dto.getPriority().name()
        );

        if (updatedRows == 0) {
            throw new RuntimeException("Task not found");
        }

        Task updatedTask = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found after update"));

        return mapper.toDto(updatedTask);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
    }

}