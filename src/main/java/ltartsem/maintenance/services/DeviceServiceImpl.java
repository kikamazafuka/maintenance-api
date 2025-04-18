package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.DeviceRequestDto;
import ltartsem.maintenance.dto.DeviceResponseDto;
import ltartsem.maintenance.exceptions.DeviceNotFoundException;
import ltartsem.maintenance.mapper.DeviceMapper;
import ltartsem.maintenance.models.Device;
import ltartsem.maintenance.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public List<DeviceResponseDto> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DeviceResponseDto> getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::toResponse);
    }

    @Override
    public DeviceResponseDto createDevice(DeviceRequestDto deviceRequest) {
        Device device = deviceMapper.toEntity(deviceRequest);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toResponse(savedDevice);
    }

    @Override
    public Optional<DeviceResponseDto> updateDevice(Long id, DeviceRequestDto deviceRequest) {
        return deviceRepository.findById(id)
                .map(device -> {
                    deviceMapper.updateEntity(device, deviceRequest);
                    Device updatedDevice = deviceRepository.save(device);
                    return deviceMapper.toResponse(updatedDevice);
                });
    }

    @Override
    public boolean deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException(id);
        }
        deviceRepository.deleteById(id);
        return true;
    }
} 