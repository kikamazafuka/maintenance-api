package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.DeviceRequest;
import ltartsem.maintenance.dto.DeviceResponse;
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
    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(deviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DeviceResponse> getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::toResponse);
    }

    @Override
    public DeviceResponse createDevice(DeviceRequest deviceRequest) {
        Device device = deviceMapper.toEntity(deviceRequest);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toResponse(savedDevice);
    }

    @Override
    public Optional<DeviceResponse> updateDevice(Long id, DeviceRequest deviceRequest) {
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