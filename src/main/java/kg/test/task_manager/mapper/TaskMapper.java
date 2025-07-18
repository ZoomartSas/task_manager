package kg.test.task_manager.mapper;

import kg.test.task_manager.dto.Request.TaskRequestDto;
import kg.test.task_manager.dto.Response.TaskResponseDto;
import kg.test.task_manager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(TaskRequestDto dto);

    TaskResponseDto toDto(Task task);
}