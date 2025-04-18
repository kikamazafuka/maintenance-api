package ltartsem.maintenance.services;

import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.models.SystemType;
import ltartsem.maintenance.repositories.OfficeDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    private final OfficeDeviceRepository officeDeviceRepository;

    @Autowired
    public MaintenanceService(OfficeDeviceRepository officeDeviceRepository) {
        this.officeDeviceRepository = officeDeviceRepository;
    }

    /**
     * Calculate the total first maintenance duration for an office and system type over a given period in months.
     */
    public Long calculateFirstMaintenanceDurationForPeriod(Office office, SystemType systemType, int months) {
        Long totalFirstMaintenanceDurationPerMonth =
                officeDeviceRepository.calculateTotalFirstMaintenanceDuration(office, systemType);

        // Multiply by the number of months
        return totalFirstMaintenanceDurationPerMonth != null ? totalFirstMaintenanceDurationPerMonth * months : 0;
    }
}

