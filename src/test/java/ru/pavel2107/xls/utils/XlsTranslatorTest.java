package ru.pavel2107.xls.utils;

import org.junit.Test;
import ru.pavel2107.xls.domain.GeologicalClass;
import ru.pavel2107.xls.domain.Section;
//import ru.pavel2107.xls.service.XlsTranslator;

import java.io.FileInputStream;
import java.util.List;

public class XlsTranslatorTest {

    @Test
    public void проверим_чтение() throws Exception{
        FileInputStream stream = new FileInputStream("D:\\Java\\IdeaProjects\\xlsClient\\files\\in\\file1.xls");
        //List<Section> list = XlsTranslator.read( stream);
        //System.out.println( list);
    }

    @Test
    public void проверим_запись(){
        Section section = new Section();
        section.setName( "test1");

        GeologicalClass g1 = new GeologicalClass();  g1.setCode("1");  g1.setName("name1");// g1.setSection( section);
        GeologicalClass g2 = new GeologicalClass();  g2.setCode("2");  g2.setName("name2");// g2.setSection( section);

        section.getGeologicalClasses().add( g1);
        section.getGeologicalClasses().add( g2);
    }
}