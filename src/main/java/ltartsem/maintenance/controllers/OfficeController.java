package ltartsem.maintenance.controllers;

import ltartsem.maintenance.dto.OfficeRequestDto;
import ltartsem.maintenance.dto.OfficeResponseDto;
import ltartsem.maintenance.services.OfficeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public List<OfficeResponseDto> getAllOffices() {
        log.info("Received request to get all offices");
        return officeService.getAllOffices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> getOfficeById(@PathVariable Long id) {
        log.info("Received request to get office with id: {}", id);
        return officeService.getOfficeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public OfficeResponseDto createOffice(@RequestBody OfficeRequestDto officeRequest) {
        log.info("Received request to create new office");
        return officeService.createOffice(officeRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> updateOffice(
            @PathVariable Long id,
            @RequestBody OfficeRequestDto officeRequest) {
        log.info("Received request to update office with id: {}", id);
        return officeService.updateOffice(id, officeRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        log.info("Received request to delete office with id: {}", id);
        if (officeService.deleteOffice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

