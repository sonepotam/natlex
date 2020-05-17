package ru.pavel2107.xls.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel2107.xls.domain.GeologicalClass;

@Repository
public interface GeoRepo extends JpaRepository<GeologicalClass, String> {
}
