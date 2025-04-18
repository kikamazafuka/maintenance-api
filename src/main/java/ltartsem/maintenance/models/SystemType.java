package ltartsem.maintenance.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class SystemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "systemTypes")
    private Set<Device> devices = new HashSet<>();

    @ManyToMany(mappedBy = "systemTypes")
    private Set<Office> offices = new HashSet<>();

    @OneToMany(mappedBy = "systemType")
    private Set<OfficeDevice> officeDevices = new HashSet<>();
}

