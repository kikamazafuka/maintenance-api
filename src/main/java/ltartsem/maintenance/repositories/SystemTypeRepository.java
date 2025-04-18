package ltartsem.maintenance.repositories;

import jdk.jfr.Registered;
import ltartsem.maintenance.models.SystemType;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface SystemTypeRepository extends JpaRepository<SystemType, Long> {
}
