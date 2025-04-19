package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.EmployeeRequestDto;
import ltartsem.maintenance.dto.EmployeeResponseDto;
import ltartsem.maintenance.exceptions.EmployeeNotFoundException;
import ltartsem.maintenance.mapper.EmployeeMapper;
import ltartsem.maintenance.models.Employee;
import ltartsem.maintenance.models.EmployeeOffice;
import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.repositories.EmployeeRepository;
import ltartsem.maintenance.repositories.OfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OfficeRepository officeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, 
                             EmployeeMapper employeeMapper,
                             OfficeRepository officeRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.officeRepository = officeRepository;
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public Optional<EmployeeResponseDto> getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id)
                .map(employee -> {
                    // Access employeeOffices to ensure they are loaded
                    employee.getEmployeeOffices().size();
                    return employeeMapper.toResponse(employee);
                });
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequest) {
        log.info("Creating new employee");
        Employee employee = employeeMapper.toEntity(employeeRequest);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created successfully with id: {}", savedEmployee.getId());
        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    public Optional<EmployeeResponseDto> updateEmployee(Long id, EmployeeRequestDto employeeRequest) {
        log.info("Updating employee with id: {}", id);
        return employeeRepository.findById(id)
                .map(employee -> {
                    employeeMapper.updateEntity(employee, employeeRequest);
                    Employee updatedEmployee = employeeRepository.save(employee);
                    log.info("Employee with id: {} updated successfully", id);
                    return employeeMapper.toResponse(updatedEmployee);
                });
    }

    @Override
    public boolean deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
        log.info("Employee with id: {} deleted successfully", id);
        return true;
    }

    @Override
    public Optional<EmployeeResponseDto> updateEmployeeOffices(Long id, Set<Long> officeIds) {
        log.info("Updating offices for employee with id: {}", id);
        return employeeRepository.findById(id)
                .map(employee -> {
                    Set<EmployeeOffice> employeeOffices = officeIds.stream()
                            .map(officeId -> {
                                Office office = officeRepository.findById(officeId)
                                        .orElseThrow(() -> new IllegalArgumentException("Office not found with id: " + officeId));
                                EmployeeOffice employeeOffice = new EmployeeOffice();
                                employeeOffice.setEmployee(employee);
                                employeeOffice.setOffice(office);
                                return employeeOffice;
                            })
                            .collect(Collectors.toSet());
                    
                    employee.getEmployeeOffices().clear();
                    employee.getEmployeeOffices().addAll(employeeOffices);
                    
                    Employee updatedEmployee = employeeRepository.save(employee);
                    log.info("Offices updated successfully for employee with id: {}", id);
                    return employeeMapper.toResponse(updatedEmployee);
                });
    }
} 