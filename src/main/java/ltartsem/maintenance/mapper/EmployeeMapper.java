package ltartsem.maintenance.mapper;

import ltartsem.maintenance.dto.EmployeeRequestDto;
import ltartsem.maintenance.dto.EmployeeResponseDto;
import ltartsem.maintenance.models.Employee;
import ltartsem.maintenance.models.Office;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDto dto) {
        Employee employee = new Employee();
        updateEntity(employee, dto);
        return employee;
    }

    public void updateEntity(Employee employee, EmployeeRequestDto dto) {
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setBirthDate(dto.getBirthDate());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setAddress(dto.getAddress());
        // Offices are managed separately through a dedicated endpoint
    }

    public EmployeeResponseDto toResponse(Employee employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setBirthDate(employee.getBirthDate());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setAddress(employee.getAddress());
        
        Set<Long> officeIds = employee.getOffices().stream()
                .map(Office::getId)
                .collect(Collectors.toSet());
        dto.setOfficeIds(officeIds);
        
        return dto;
    }
} 