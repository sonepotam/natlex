package ru.pavel2107.gpb.service;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.pavel2107.xls.domain.GeologicalClass;
import ru.pavel2107.xls.domain.Section;
import ru.pavel2107.xls.repo.SectionRepo;
import ru.pavel2107.xls.service.SectionService;
import ru.pavel2107.xls.service.SectionServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest( classes = {SectionServiceImpl.class})
@TestPropertySource( "classpath:application.yml")
@Import({SectionServiceImpl.class, SectionRepo.class})
@DisplayName( "Test RequestService")
public class RequestServiceImplTest {

    @Autowired SectionService sectionService;

    @Test
    public void проверим_запись(){
        Section section = new Section();
        section.setName( "test1");

        GeologicalClass g1 = new GeologicalClass();  g1.setCode("1");  g1.setName("name1");
        GeologicalClass g2 = new GeologicalClass();  g2.setCode("2");  g2.setName("name2");

        section.getGeologicalClasses().add( g1);
        section.getGeologicalClasses().add( g2);

        section = sectionService.save( section);
        System.out.println( section);
    }


}