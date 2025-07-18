package kg.test.task_manager.service;

import kg.test.task_manager.dto.ExternalObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiService.class);

    @Value("${external-api.url}")
    private String url;

    private final RestTemplate restTemplate;

    public void fetchAndLogObjects() {
        try {
            ResponseEntity<ExternalObject[]> response = restTemplate.getForEntity(url, ExternalObject[].class);
            ExternalObject[] objects = response.getBody();

            if (objects != null) {
                for (ExternalObject object : objects) {
                    log.info("ID: {}, Name: {}, Data: {}", object.getId(), object.getName(), object.getData());
                }
            } else {
                log.warn("Пустой ответ от внешнего API");
            }
        } catch (RestClientException e) {
            log.error("Ошибка при вызове внешнего API: {}", e.getMessage(), e);
        }
    }
}



