package ru.pavel2107.xls.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pavel2107.xls.фьютчерс.Обертка;
import ru.pavel2107.xls.фьютчерс.Обработчик;
import ru.pavel2107.xls.фьютчерс.Статусы_загрузки;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class XlsController {
    static private Logger logger = LogManager.getLogger();

    AtomicInteger counter = new AtomicInteger(0);
    ConcurrentHashMap<Integer, Future<Обертка>> map = new ConcurrentHashMap<>();

    private Обработчик processor;

    @Autowired
    public XlsController( Обработчик processor){
        this.processor = processor;
    }

    @PostMapping( value = "/import")
    public Integer загрузить_файл( @RequestParam("file") MultipartFile файл){
        logger.info( "загружаем файл " + файл.getOriginalFilename());
        Integer ptr = counter.incrementAndGet();
        map.put( ptr, processor.загрузить_эксель( ptr, файл));
        logger.info( "Загрузке файла " + файл.getName() + "присвоен номер " + ptr);
        return ptr;
    }

    @GetMapping( value = "/import/{id}")
    public String статус_загрузки_файла( @PathVariable( value = "id") Integer id)throws Exception{
        logger.info( "получить статус загрузки файла # " + id );
        String result = getStatus( id);
        logger.info( "статус загрузки файла # " + id + " = " + result);
        return result;
    }

    @GetMapping( value = "/export")
    public Integer начинаем_экспорт(){
        logger.info( "начинаем экспорт");
        Integer ptr = counter.incrementAndGet();
        map.put( ptr, processor.выгрузить_эксель( ptr));
        logger.info( "задаче по выгрузке присвоен номер # " + ptr);
        return ptr;
    }

    @GetMapping( value = "/export/{id}")
    public String статус_выгрузки_файла( @PathVariable( value = "id") Integer id){
        logger.info( "получить статус выгрузки файла # " + id) ;
        String result = getStatus( id);
        logger.info( "Выгрузка файла " + id + " имеет статус " + result);
        return getStatus( id);
    }

    @GetMapping( value = "/export/{id}/file")
    public ResponseEntity выгрузить_файл(@PathVariable( value = "id") Integer id)throws Exception{
        logger.info( "получить статус выгрузки файла # " + id) ;
        String result = getStatus( id);
        logger.info( "Выгрузка файла " + id + " имеет статус " + result);

        if( result.equals( Статусы_загрузки.ГОТОВО)) {
            Future<Обертка> обертка = map.get( id);
            ByteArrayResource resource = new ByteArrayResource( обертка.get().getФайлик().getBytes());
            logger.info( "получить статус выгрузки файла # " + id) ;

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=output.xls" )
                    .contentLength(обертка.get().getФайлик().getBytes().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } else
        {
            return ResponseEntity
                    .status( HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( "Выгрузка файла " + id + " имеет статус " + result);
        }
    }

    private String getStatus( Integer id) {
        Future<Обертка> обертка = map.get( id);
        try {
            if( !обертка.isDone()){
                return Статусы_загрузки.РАБОТАЕТ;
            }
            return обертка.get().getСостояние();
        } catch (Exception e) {
            return Статусы_загрузки.ОШИБКА;
        }
    }
}
