package ltartsem.maintenance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Device request data")
public record DeviceRequest(
    @NotBlank(message = "Device name is required")
    @Schema(description = "Name of the device", example = "Printer XYZ-2000")
    String name,

    @NotNull(message = "First maintenance duration is required")
    @PositiveOrZero(message = "First maintenance duration must be positive or zero")
    @Schema(description = "Duration of first maintenance in minutes", example = "30")
    Long firstMaintenanceDurationMinutes,

    @NotNull(message = "Second maintenance duration is required")
    @PositiveOrZero(message = "Second maintenance duration must be positive or zero")
    @Schema(description = "Duration of second maintenance in minutes", example = "60")
    Long secondMaintenanceDurationMinutes
) {} 