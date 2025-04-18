package ltartsem.maintenance.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Schema(description = "Device entity representing a device that requires maintenance")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the device", example = "1")
    private Long id;

    @NotBlank(message = "Device name is required")
    @Schema(description = "Name of the device", example = "Printer XYZ-2000")
    private String name;

    @Column(name = "first_maintenance_duration_minutes")
    @NotNull(message = "First maintenance duration is required")
    @PositiveOrZero(message = "First maintenance duration must be positive or zero")
    @Schema(description = "Duration of first maintenance in minutes", example = "30")
    private Long firstMaintenanceDurationMinutes;

    @Column(name = "second_maintenance_duration_minutes")
    @NotNull(message = "Second maintenance duration is required")
    @PositiveOrZero(message = "Second maintenance duration must be positive or zero")
    @Schema(description = "Duration of second maintenance in minutes", example = "60")
    private Long secondMaintenanceDurationMinutes;

    @ManyToMany
    @JoinTable(
            name = "device_system_type",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "system_type_id")
    )
    @Schema(description = "Set of system types this device belongs to")
    private Set<SystemType> systemTypes = new HashSet<>();

    @OneToMany(mappedBy = "device")
    @Schema(description = "Set of office devices associated with this device type")
    private Set<OfficeDevice> officeDevices = new HashSet<>();
}

