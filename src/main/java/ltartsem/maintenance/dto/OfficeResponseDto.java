package ltartsem.maintenance.dto;

import java.util.Set;

public record OfficeResponseDto(
    Long id,
    String name,
    String address,
    Set<Long> employeeIds,
    Set<Long> systemTypeIds
) {} 