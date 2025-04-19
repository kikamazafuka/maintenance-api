package ltartsem.maintenance.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ltartsem.maintenance.dto.OfficeRequestDto;
import ltartsem.maintenance.dto.OfficeResponseDto;
import ltartsem.maintenance.exceptions.ErrorResponse;
import ltartsem.maintenance.services.OfficeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/office")
@Tag(name = "Office Management", description = "APIs for managing offices")
public class OfficeController {

    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @Operation(summary = "Get all offices", description = "Retrieves a list of all offices")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all offices",
            content = @Content(schema = @Schema(implementation = OfficeResponseDto.class, type = "array")))
    @GetMapping
    public List<OfficeResponseDto> getAllOffices() {
        log.info("Received request to get all offices");
        return officeService.getAllOffices();
    }

    @Operation(summary = "Get office by ID", description = "Retrieves a specific office by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the office",
                    content = @Content(schema = @Schema(implementation = OfficeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Office not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> getOfficeById(
            @Parameter(description = "ID of the office to retrieve", example = "1")
            @PathVariable Long id) {
        log.info("Received request to get office with id: {}", id);
        return officeService.getOfficeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new office", description = "Creates a new office with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the office",
                    content = @Content(schema = @Schema(implementation = OfficeResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public OfficeResponseDto createOffice(
            @Parameter(description = "Office object to be created")
            @RequestBody OfficeRequestDto officeRequest) {
        log.info("Received request to create new office");
        return officeService.createOffice(officeRequest);
    }

    @Operation(summary = "Update an office", description = "Updates an existing office with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the office",
                    content = @Content(schema = @Schema(implementation = OfficeResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Office not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> updateOffice(
            @Parameter(description = "ID of the office to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated office object")
            @RequestBody OfficeRequestDto officeRequest) {
        log.info("Received request to update office with id: {}", id);
        return officeService.updateOffice(id, officeRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an office", description = "Deletes an office by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the office"),
            @ApiResponse(responseCode = "404", description = "Office not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffice(
            @Parameter(description = "ID of the office to delete", example = "1")
            @PathVariable Long id) {
        log.info("Received request to delete office with id: {}", id);
        if (officeService.deleteOffice(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

