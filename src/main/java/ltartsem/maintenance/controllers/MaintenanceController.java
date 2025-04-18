package ltartsem.maintenance.controllers;

import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.models.SystemType;
import ltartsem.maintenance.repositories.OfficeRepository;
import ltartsem.maintenance.repositories.SystemTypeRepository;
import ltartsem.maintenance.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    private final OfficeRepository officeRepository;
    private final SystemTypeRepository systemTypeRepository;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService, OfficeRepository officeRepository, SystemTypeRepository systemTypeRepository) {
        this.maintenanceService = maintenanceService;
        this.officeRepository = officeRepository;
        this.systemTypeRepository = systemTypeRepository;
    }

    @GetMapping("/first-maintenance-duration")
    public ResponseEntity<Long> getFirstMaintenanceDuration(
            @RequestParam Long officeId,
            @RequestParam Long systemTypeId,
            @RequestParam int months) {

        Office office = officeRepository.findById(officeId).orElse(null);
        SystemType systemType = systemTypeRepository.findById(systemTypeId).orElse(null);

        if (office == null || systemType == null) {
            return ResponseEntity.notFound().build();
        }

        Long totalDuration = maintenanceService.calculateFirstMaintenanceDurationForPeriod(office, systemType, months);
        return ResponseEntity.ok(totalDuration);
    }
}

