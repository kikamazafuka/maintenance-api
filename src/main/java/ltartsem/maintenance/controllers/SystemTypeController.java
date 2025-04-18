package ltartsem.maintenance.controllers;

import ltartsem.maintenance.models.SystemType;
import ltartsem.maintenance.repositories.SystemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-types")
public class SystemTypeController {

    private final SystemTypeRepository systemTypeRepository;

    @Autowired
    public SystemTypeController(SystemTypeRepository systemTypeRepository) {
        this.systemTypeRepository = systemTypeRepository;
    }

    @GetMapping
    public List<SystemType> getAllSystemTypes() {
        return systemTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemType> getSystemTypeById(@PathVariable Long id) {
        return systemTypeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SystemType createSystemType(@RequestBody SystemType systemType) {
        return systemTypeRepository.save(systemType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemType> updateSystemType(@PathVariable Long id, @RequestBody SystemType systemTypeDetails) {
        return systemTypeRepository.findById(id)
                .map(systemType -> {
                    systemType.setName(systemTypeDetails.getName());
                    return ResponseEntity.ok(systemTypeRepository.save(systemType));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystemType(@PathVariable Long id) {
        if (systemTypeRepository.existsById(id)) {
            systemTypeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

