package ltartsem.maintenance.mapper;

import ltartsem.maintenance.dto.OfficeRequestDto;
import ltartsem.maintenance.dto.OfficeResponseDto;
import ltartsem.maintenance.models.Employee;
import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.models.SystemType;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OfficeMapper {

    public Office toEntity(OfficeRequestDto dto) {
        Office office = new Office();
        updateEntity(office, dto);
        return office;
    }

    public void updateEntity(Office office, OfficeRequestDto dto) {
        office.setName(dto.name());
        office.setAddress(dto.address());
    }

    public OfficeResponseDto toResponse(Office office) {
        Set<Long> employeeIds = office.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());

        Set<Long> systemTypeIds = office.getSystemTypes().stream()
                .map(SystemType::getId)
                .collect(Collectors.toSet());

        return new OfficeResponseDto(
            office.getId(),
            office.getName(),
            office.getAddress(),
            employeeIds,
            systemTypeIds
        );
    }
} 