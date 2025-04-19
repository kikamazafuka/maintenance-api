package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.OfficeRequestDto;
import ltartsem.maintenance.dto.OfficeResponseDto;
import ltartsem.maintenance.exceptions.OfficeNotFoundException;
import ltartsem.maintenance.mapper.OfficeMapper;
import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.repositories.OfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;
    private final OfficeMapper officeMapper;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository, OfficeMapper officeMapper) {
        this.officeRepository = officeRepository;
        this.officeMapper = officeMapper;
    }

    @Override
    public List<OfficeResponseDto> getAllOffices() {
        log.info("Fetching all offices");
        return officeRepository.findAll().stream()
                .map(officeMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<OfficeResponseDto> getOfficeById(Long id) {
        log.info("Fetching office with id: {}", id);
        return officeRepository.findById(id)
                .map(officeMapper::toResponse);
    }

    @Override
    public OfficeResponseDto createOffice(OfficeRequestDto officeRequest) {
        log.info("Creating new office");
        Office office = officeMapper.toEntity(officeRequest);
        Office savedOffice = officeRepository.save(office);
        log.info("Office created successfully with id: {}", savedOffice.getId());
        return officeMapper.toResponse(savedOffice);
    }

    @Override
    public Optional<OfficeResponseDto> updateOffice(Long id, OfficeRequestDto officeRequest) {
        log.info("Updating office with id: {}", id);
        return officeRepository.findById(id)
                .map(office -> {
                    officeMapper.updateEntity(office, officeRequest);
                    Office updatedOffice = officeRepository.save(office);
                    log.info("Office with id: {} updated successfully", id);
                    return officeMapper.toResponse(updatedOffice);
                });
    }

    @Override
    public boolean deleteOffice(Long id) {
        log.info("Deleting office with id: {}", id);
        if (!officeRepository.existsById(id)) {
            throw new OfficeNotFoundException(id);
        }
        officeRepository.deleteById(id);
        log.info("Office with id: {} deleted successfully", id);
        return true;
    }
} 