package ltartsem.maintenance;

import ltartsem.maintenance.dto.OfficeRequestDto;
import ltartsem.maintenance.dto.OfficeResponseDto;
import ltartsem.maintenance.exceptions.OfficeNotFoundException;
import ltartsem.maintenance.mapper.OfficeMapper;
import ltartsem.maintenance.models.Office;
import ltartsem.maintenance.repositories.OfficeRepository;
import ltartsem.maintenance.services.OfficeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfficeServiceImplTest {
    @Mock
    private OfficeRepository officeRepository;
    @Mock
    private OfficeMapper officeMapper;
    @InjectMocks
    private OfficeServiceImpl officeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOffices_returnsList() {
        Office office = new Office();
        OfficeResponseDto dto = new OfficeResponseDto(1L, "name", "address", Set.of(), Set.of());
        when(officeRepository.findAll()).thenReturn(List.of(office));
        when(officeMapper.toResponse(office)).thenReturn(dto);
        List<OfficeResponseDto> result = officeService.getAllOffices();
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getOfficeById_found() {
        Office office = new Office();
        OfficeResponseDto dto = new OfficeResponseDto(1L, "name", "address", Set.of(), Set.of());
        when(officeRepository.findById(1L)).thenReturn(Optional.of(office));
        when(officeMapper.toResponse(office)).thenReturn(dto);
        Optional<OfficeResponseDto> result = officeService.getOfficeById(1L);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void getOfficeById_notFound() {
        when(officeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<OfficeResponseDto> result = officeService.getOfficeById(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void createOffice_success() {
        OfficeRequestDto req = new OfficeRequestDto("name", "address");
        Office office = new Office();
        Office saved = new Office();
        saved.setId(1L);
        OfficeResponseDto dto = new OfficeResponseDto(1L, "name", "address", Set.of(), Set.of());
        when(officeMapper.toEntity(req)).thenReturn(office);
        when(officeRepository.save(office)).thenReturn(saved);
        when(officeMapper.toResponse(saved)).thenReturn(dto);
        OfficeResponseDto result = officeService.createOffice(req);
        assertEquals(dto, result);
    }

    @Test
    void updateOffice_found() {
        OfficeRequestDto req = new OfficeRequestDto("name", "address");
        Office office = new Office();
        Office updated = new Office();
        OfficeResponseDto dto = new OfficeResponseDto(1L, "name", "address", Set.of(), Set.of());
        when(officeRepository.findById(1L)).thenReturn(Optional.of(office));
        doAnswer(invocation -> {
            // Simulate update
            return null;
        }).when(officeMapper).updateEntity(office, req);
        when(officeRepository.save(office)).thenReturn(updated);
        when(officeMapper.toResponse(updated)).thenReturn(dto);
        Optional<OfficeResponseDto> result = officeService.updateOffice(1L, req);
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void updateOffice_notFound() {
        OfficeRequestDto req = new OfficeRequestDto("name", "address");
        when(officeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<OfficeResponseDto> result = officeService.updateOffice(1L, req);
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteOffice_success() {
        when(officeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(officeRepository).deleteById(1L);
        boolean result = officeService.deleteOffice(1L);
        assertTrue(result);
    }

    @Test
    void deleteOffice_notFound() {
        when(officeRepository.existsById(1L)).thenReturn(false);
        assertThrows(OfficeNotFoundException.class, () -> officeService.deleteOffice(1L));
    }
} 