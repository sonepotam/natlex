package ru.pavel2107.xls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pavel2107.xls.domain.Section;
import ru.pavel2107.xls.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
public class SectionController{

    private SectionService sectionService;

    @Autowired
    public SectionController( SectionService sectionService){
        this.sectionService = sectionService;
    }

    @GetMapping( value = "/rest/sections", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Section> listSections(){
        List<Section> sectionList = sectionService.findAll();
        return sectionList;
    }

    @DeleteMapping( value = "/rest/sections")
    @ResponseStatus( value = HttpStatus.NO_CONTENT)
    public void delete(@RequestParam( value = "name") String name){
        sectionService.delete( name);
    }

    @GetMapping( value = "/rest/sections/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Section get( @PathVariable( value = "name") String name){
        Section section = sectionService.find( name);
        return section;
    }

    @PostMapping( value = "/rest/sections", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus( value = HttpStatus.NO_CONTENT)
    public void save( @RequestBody Section section){
        sectionService.save( section);
    }
}
