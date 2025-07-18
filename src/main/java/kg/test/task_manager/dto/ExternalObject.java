package kg.test.task_manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalObject {
    private String id;
    private String name;
    private Map<String, Object> data;
}

