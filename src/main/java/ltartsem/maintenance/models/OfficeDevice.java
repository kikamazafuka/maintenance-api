package ltartsem.maintenance.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OfficeDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @ManyToOne
    @JoinColumn(name = "system_type_id")
    private SystemType systemType;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    // Maintenance time in minutes for this device instance
    private Long firstMaintenanceDurationMinutes;
    private Long secondMaintenanceDurationMinutes;

}

