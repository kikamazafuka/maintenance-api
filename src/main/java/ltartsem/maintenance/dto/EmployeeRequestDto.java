package ltartsem.maintenance.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestDto {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
} 