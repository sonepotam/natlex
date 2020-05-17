package ru.pavel2107.xls.service;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pavel2107.xls.config.Config;
import ru.pavel2107.xls.domain.GeologicalClass;
import ru.pavel2107.xls.domain.Section;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//
// честно говоря никогда не работал с Apache POI
//
// https://www.java67.com/2014/09/how-to-read-write-xlsx-file-in-java-apache-poi-example.html
// https://tproger.ru/translations/how-to-read-write-excel-file-java-poi-example/
// https://poi.apache.org/components/spreadsheet/quick-guide.html#CellContents
//

@Service
public class TranslatorServiceImpl implements TranslatorService{

    private static Logger logger = LogManager.getLogger();
    private Config config;

    @Autowired
    public TranslatorServiceImpl( Config config){
        this.config = config;
    }

    @Override
    public List<Section> read(InputStream stream) throws Exception {
        Workbook wb = WorkbookFactory.create( stream);
        Sheet sheet = wb.getSheetAt( 0);
        List<Section> sections = new ArrayList<>();
        logger.info( "начат разбор стрима");
        //
        // если файл пришел пустым, то на выход
        //
        if( sheet.getLastRowNum() < 2) return sections;
        //
        // если количество столбцов нечетное, то на выход
        //
        if( sheet.getRow(0).getLastCellNum() % 2 == 0){
           throw new Exception( "Нарушение формата. Неверное количество столбцов");
        } ;
        //
        // анализируем поступивший файл
        //
        for( int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
            logger.info( "ожидание. анализируем строку " + rowNum);
            sleep();
            logger.info( "ожидание завершено");

            Row row = sheet.getRow( rowNum);
            //
            // заполняем название секции
            //
            Cell cell = row.getCell( 0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if( cell == null){
                throw new Exception( "Нарушение формата. Нет названия секции");
            }
            Section section = new Section();
            section.setName( cell.getStringCellValue());
            for( int cellNum = 1; cellNum <= row.getLastCellNum(); cellNum++){
                //
                // получаем значение ячеек
                //
                Cell className = row.getCell( cellNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if( className == null){
                    Cell c = row.getCell( ++cellNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if( c == null) continue;
                    throw new Exception( "Нарушение формата. Нет class name");
                }
                Cell classCode = row.getCell( ++cellNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if( classCode == null){
                    throw new Exception( "Нарушение формата. Нет class code");
                }
                GeologicalClass geo = new GeologicalClass();
                geo.setName( className.getStringCellValue());
                geo.setCode( classCode.getStringCellValue());
                section.getGeologicalClasses().add( geo);
            }
            sections.add( section);
        }
        return  sections;
    }

    @Override
    public ByteOutputStream write( List <Section> sections) throws Exception{
        ByteOutputStream stream = new ByteOutputStream();
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //
        // первая строка
        //
        Row firstRow = sheet.createRow( 0);
        firstRow
                .createCell( 0)
                .setCellValue( "section name");
        //
        // ищем количество элементов в самом большом geo
        //
        int columns = sections
                .stream()
                .mapToInt( section -> section.getGeologicalClasses().toArray().length)
                .max().orElse( 0);
        //
        // формируем заголовок
        //
        for (int colPtr = 0; colPtr < columns; colPtr++){
            firstRow.createCell( 1 + 2 * colPtr).setCellValue( "Class name " + (colPtr + 1));   // название строки
            firstRow.createCell( 2 + 2 * colPtr).setCellValue( "Class code " + (colPtr + 1));   // код строки
        }
        //
        // вообще не понятно что и как выводить ???
        //
        final Integer[] i = {1};
        sections
                .stream()
                .forEach( section -> {
                    sleep();
                    //
                    // выводим название строки
                    //
                    Row row = sheet.createRow( i[0]++);
                    row.createCell( 0).setCellValue( section.getName());
                    //
                    // выводим содержимов
                    //
                    final Integer[] j = {1};
                    section.getGeologicalClasses()
                            .stream()
                            .forEach( g ->{
                                row.createCell( j[0]++).setCellValue( g.getName());
                                row.createCell( j[0]++).setCellValue( g.getCode());
                    });
                });
        //
        // загоняем в поток книгу
        //
        wb.write( stream);

        return stream;
    }

    private  void sleep(){
        try {
            Thread.sleep( config.getPause() * 1000);
        }
        catch ( InterruptedException e){}
    }
}
