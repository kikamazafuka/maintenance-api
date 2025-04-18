package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.DeviceRequestDto;
import ltartsem.maintenance.dto.DeviceResponseDto;
import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<DeviceResponseDto> getAllDevices();
    Optional<DeviceResponseDto> getDeviceById(Long id);
    DeviceResponseDto createDevice(DeviceRequestDto deviceRequest);
    Optional<DeviceResponseDto> updateDevice(Long id, DeviceRequestDto deviceRequest);
    boolean deleteDevice(Long id);
} 