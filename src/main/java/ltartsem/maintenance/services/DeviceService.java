package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.DeviceRequest;
import ltartsem.maintenance.dto.DeviceResponse;
import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<DeviceResponse> getAllDevices();
    Optional<DeviceResponse> getDeviceById(Long id);
    DeviceResponse createDevice(DeviceRequest deviceRequest);
    Optional<DeviceResponse> updateDevice(Long id, DeviceRequest deviceRequest);
    boolean deleteDevice(Long id);
} 