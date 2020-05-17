package ru.pavel2107.xls.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel2107.xls.domain.Section;
import java.util.List;

@Repository
public interface SectionRepo extends JpaRepository<Section, String> {
    List<Section> findSectionsByGeologicalClassesCode( String code);
}
