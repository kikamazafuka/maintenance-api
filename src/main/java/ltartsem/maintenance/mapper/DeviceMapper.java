package ltartsem.maintenance.mapper;

import ltartsem.maintenance.dto.DeviceRequestDto;
import ltartsem.maintenance.dto.DeviceResponseDto;
import ltartsem.maintenance.models.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device toEntity(DeviceRequestDto request) {
        Device device = new Device();
        device.setName(request.name());
        device.setFirstMaintenanceDurationMinutes(request.firstMaintenanceDurationMinutes());
        device.setSecondMaintenanceDurationMinutes(request.secondMaintenanceDurationMinutes());
        return device;
    }

    public DeviceResponseDto toResponse(Device device) {
        return new DeviceResponseDto(
            device.getId(),
            device.getName(),
            device.getFirstMaintenanceDurationMinutes(),
            device.getSecondMaintenanceDurationMinutes()
        );
    }

    public void updateEntity(Device device, DeviceRequestDto request) {
        device.setName(request.name());
        device.setFirstMaintenanceDurationMinutes(request.firstMaintenanceDurationMinutes());
        device.setSecondMaintenanceDurationMinutes(request.secondMaintenanceDurationMinutes());
    }
} 