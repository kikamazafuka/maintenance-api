package ltartsem.maintenance.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import ltartsem.maintenance.dto.DeviceRequest;
import ltartsem.maintenance.dto.DeviceResponse;
import ltartsem.maintenance.exceptions.ErrorResponse;
import ltartsem.maintenance.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device Management", description = "APIs for managing devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Get all devices", description = "Retrieves a list of all devices")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all devices",
            content = @Content(schema = @Schema(implementation = DeviceResponse.class, type = "array")))
    @GetMapping
    public List<DeviceResponse> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @Operation(summary = "Get device by ID", description = "Retrieves a specific device by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the device",
                    content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(
            @Parameter(description = "ID of the device to retrieve", example = "1")
            @PathVariable Long id) {
        log.info("Retrieving device by id: {}", id);
        return deviceService.getDeviceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new device", description = "Creates a new device with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the device",
                    content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public DeviceResponse createDevice(
            @Parameter(description = "Device object to be created")
            @RequestBody DeviceRequest deviceRequest) {
        return deviceService.createDevice(deviceRequest);
    }

    @Operation(summary = "Update a device", description = "Updates an existing device with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the device",
                    content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @Parameter(description = "ID of the device to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated device object")
            @RequestBody DeviceRequest deviceRequest) {
        return deviceService.updateDevice(id, deviceRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a device", description = "Deletes a device by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the device"),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(
            @Parameter(description = "ID of the device to delete", example = "1")
            @PathVariable Long id) {
        if (deviceService.deleteDevice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

