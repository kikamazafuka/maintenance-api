package ltartsem.maintenance.controllers;

import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.repositories.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeController {

    private final OfficeRepository officeRepository;

    @Autowired
    public OfficeController(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @GetMapping
    public List<Office> getAllOffices() {
        return officeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Office> getOfficeById(@PathVariable Long id) {
        return officeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Office createOffice(@RequestBody Office office) {
        return officeRepository.save(office);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@PathVariable Long id, @RequestBody Office officeDetails) {
        return officeRepository.findById(id)
                .map(office -> {
                    office.setName(officeDetails.getName());
                    office.setAddress(officeDetails.getAddress());
                    return ResponseEntity.ok(officeRepository.save(office));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long id) {
        if (officeRepository.existsById(id)) {
            officeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

