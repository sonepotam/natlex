package ru.pavel2107.xls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pavel2107.xls.domain.Section;
import ru.pavel2107.xls.repo.SectionRepo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    private SectionRepo repository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public SectionServiceImpl( SectionRepo repository){
        this.repository = repository;
    }

    @Override
    public Section save(Section section) {
        final Section s = section;
        section.getGeologicalClasses()
               .stream()
               .forEach( g -> g.setSection_name( s.getName()));

        section =repository.save( section);
        return section;
    }

    @Override
    public void delete(String name) {
        Section section = find( name);
        if (section != null) {
            repository.delete( section);
        }
    }

    @Override
    public Section find(String name) {
        return repository.findById( name).orElse( null);
    }

    @Override
    public List<Section> findAll() {
        return repository.findAll();
    }

    @Override
    public List<String> findByGeoCode(String code) {
        TypedQuery<String> query = em
                .createQuery( "select distinct geo.section_name from GeologicalClass geo where geo.code = :code", String.class)
                .setParameter( "code", code);
        List<String> sections =  query.getResultList();
        return sections;
    }
}
