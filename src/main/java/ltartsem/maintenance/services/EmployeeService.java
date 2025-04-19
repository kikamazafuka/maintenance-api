package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.EmployeeRequestDto;
import ltartsem.maintenance.dto.EmployeeResponseDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeResponseDto> getAllEmployees();
    Optional<EmployeeResponseDto> getEmployeeById(Long id);
    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequest);
    Optional<EmployeeResponseDto> updateEmployee(Long id, EmployeeRequestDto employeeRequest);
    boolean deleteEmployee(Long id);
} 