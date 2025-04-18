package ltartsem.maintenance.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @ManyToMany(mappedBy = "offices")
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "office_system_type",
            joinColumns = @JoinColumn(name = "office_id"),
            inverseJoinColumns = @JoinColumn(name = "system_type_id")
    )
    private Set<SystemType> systemTypes = new HashSet<>();

    @OneToMany(mappedBy = "office")
    private Set<OfficeDevice> officeDevices = new HashSet<>();
}

