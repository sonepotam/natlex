package ru.pavel2107.xls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pavel2107.xls.service.SectionService;

import java.util.List;

@RestController
public class ByCodeController {
    private SectionService sectionService;

    @Autowired
    public ByCodeController( SectionService sectionService){
        this.sectionService = sectionService;
    }

    @GetMapping( value = "/rest/sections/by-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> get(@RequestParam( value = "code") String code){
        List<String> sections = sectionService.findByGeoCode( code);
        return sections;
    }
}
