package az.inventory.inventorymanagementapi.repository;

import az.inventory.inventorymanagementapi.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

public interface TaxRepository extends JpaRepository<Tax, Long>, JpaSpecificationExecutor<Tax> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(@Param("name") String name, @Param("id") Long id);
}
