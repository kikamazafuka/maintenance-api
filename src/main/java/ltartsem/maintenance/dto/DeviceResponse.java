package ltartsem.maintenance.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Device response data")
public record DeviceResponse(
    @Schema(description = "Unique identifier of the device", example = "1")
    Long id,

    @Schema(description = "Name of the device", example = "Printer XYZ-2000")
    String name,

    @Schema(description = "Duration of first maintenance in minutes", example = "30")
    Long firstMaintenanceDurationMinutes,

    @Schema(description = "Duration of second maintenance in minutes", example = "60")
    Long secondMaintenanceDurationMinutes,

    @Schema(description = "Set of system type IDs this device belongs to")
    Set<Long> systemTypeIds
) {} 