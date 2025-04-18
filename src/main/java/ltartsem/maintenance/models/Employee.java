package ltartsem.maintenance.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "office_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "office_id")
    )
    private Set<Office> offices = new HashSet<>();

}

