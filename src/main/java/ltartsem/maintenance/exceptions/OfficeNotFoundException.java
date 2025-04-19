package ltartsem.maintenance.exceptions;

public class OfficeNotFoundException extends RuntimeException {
    public OfficeNotFoundException(Long id) {
        super("Office not found with id: " + id);
    }
} 