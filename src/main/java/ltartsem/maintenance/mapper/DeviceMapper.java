package ltartsem.maintenance.mapper;

import ltartsem.maintenance.dto.DeviceRequest;
import ltartsem.maintenance.dto.DeviceResponse;
import ltartsem.maintenance.models.Device;
import ltartsem.maintenance.models.SystemType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceRequest request) {
        Device device = new Device();
        device.setName(request.name());
        device.setFirstMaintenanceDurationMinutes(request.firstMaintenanceDurationMinutes());
        device.setSecondMaintenanceDurationMinutes(request.secondMaintenanceDurationMinutes());
        return device;
    }

    public DeviceResponse toResponse(Device device) {
        return new DeviceResponse(
            device.getId(),
            device.getName(),
            device.getFirstMaintenanceDurationMinutes(),
            device.getSecondMaintenanceDurationMinutes(),
            device.getSystemTypes().stream()
                .map(SystemType::getId)
                .collect(Collectors.toSet())
        );
    }

    public void updateEntity(Device device, DeviceRequest request) {
        device.setName(request.name());
        device.setFirstMaintenanceDurationMinutes(request.firstMaintenanceDurationMinutes());
        device.setSecondMaintenanceDurationMinutes(request.secondMaintenanceDurationMinutes());
    }
} 