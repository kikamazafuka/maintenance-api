package ltartsem.maintenance.repositories;

import ltartsem.maintenance.models.Device;
import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.models.OfficeDevice;
import ltartsem.maintenance.models.SystemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeDeviceRepository extends JpaRepository<OfficeDevice, Long> {

    List<OfficeDevice> findByOfficeAndSystemType(Office office, SystemType systemType);

    @Query("SELECT COUNT(od) FROM OfficeDevice od " +
            "WHERE od.office = :office " +
            "AND od.systemType = :systemType " +
            "AND od.device = :device")
    Long countDevicesInOfficeSystem(@Param("office") Office office,
                                    @Param("systemType") SystemType systemType,
                                    @Param("device") Device device);

    @Query("SELECT SUM(od.firstMaintenanceDurationMinutes) " +
            "FROM OfficeDevice od " +
            "WHERE od.office = :office AND od.systemType = :systemType")
    Long calculateTotalFirstMaintenanceDuration(
            @Param("office") Office office,
            @Param("systemType") SystemType systemType
    );

}
