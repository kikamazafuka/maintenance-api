package ltartsem.maintenance.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Device response data")
public record DeviceResponseDto(
    @Schema(description = "Unique identifier of the device", example = "1")
    Long id,

    @Schema(description = "Name of the device", example = "Printer XYZ-2000")
    String name,

    @Schema(description = "Duration of first maintenance in minutes", example = "30")
    Long firstMaintenanceDurationMinutes,

    @Schema(description = "Duration of second maintenance in minutes", example = "60")
    Long secondMaintenanceDurationMinutes

) {} 