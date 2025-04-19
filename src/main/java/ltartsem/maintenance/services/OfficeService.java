package ltartsem.maintenance.services;

import ltartsem.maintenance.dto.OfficeRequestDto;
import ltartsem.maintenance.dto.OfficeResponseDto;

import java.util.List;
import java.util.Optional;

public interface OfficeService {
    List<OfficeResponseDto> getAllOffices();
    Optional<OfficeResponseDto> getOfficeById(Long id);
    OfficeResponseDto createOffice(OfficeRequestDto officeRequest);
    Optional<OfficeResponseDto> updateOffice(Long id, OfficeRequestDto officeRequest);
    boolean deleteOffice(Long id);
} 