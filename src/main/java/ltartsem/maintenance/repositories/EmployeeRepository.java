package ltartsem.maintenance.repositories;

import ltartsem.maintenance.models.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.employeeOffices eo LEFT JOIN FETCH eo.office WHERE e.id = :id")
    Optional<Employee> findByIdWithOffices(@Param("id") Long id);

    @EntityGraph(attributePaths = {"employeeOffices", "employeeOffices.office"})
    Optional<Employee> findById(Long id);
}
