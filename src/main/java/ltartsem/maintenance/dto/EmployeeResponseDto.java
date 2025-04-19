package ltartsem.maintenance.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private Set<EmployeeOfficeDto> offices;
} 