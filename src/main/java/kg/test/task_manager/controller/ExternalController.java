package kg.test.task_manager.controller;

import kg.test.task_manager.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalApiService externalApiService;

    @GetMapping("/fetch-objects")
    public ResponseEntity<String> fetchObjects() {
        externalApiService.fetchAndLogObjects();
        return ResponseEntity.ok("Objects fetched and logged");
    }
}
