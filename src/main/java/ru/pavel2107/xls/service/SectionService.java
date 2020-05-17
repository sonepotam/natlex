package ru.pavel2107.xls.service;

import ru.pavel2107.xls.domain.Section;
import java.util.List;

public interface SectionService{
    Section save( Section section);
    void delete( String name);
    Section find( String name);
    List<Section> findAll();
    List<String> findByGeoCode(String code);
}
