package ru.pavel2107.xls.фьютчерс;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.pavel2107.xls.domain.Section;
import ru.pavel2107.xls.service.SectionService;
import ru.pavel2107.xls.service.TranslatorService;
import java.util.List;
import java.util.concurrent.Future;

@Component
public class Обработчик {

    private SectionService сервис;
    private TranslatorService транслятор;

    @Autowired
    public Обработчик(SectionService сервис, TranslatorService транслятор){
        this.сервис = сервис;
        this.транслятор = транслятор;
    }

    @Async
    public Future<Обертка> загрузить_эксель(Integer идэ, MultipartFile файл){
        Обертка обертка = new Обертка();
        обертка.setИдэ( идэ);
        обертка.setСостояние( Статусы_загрузки.РАБОТАЕТ);
        try {
            //
            // читаем полученный файл
            //
            List<Section> секции = транслятор.read(файл.getInputStream());
            //
            // загружаем его в базу данных
            //
            секции
                    .stream()
                    .forEach( элемент ->сервис.save( элемент));
            //
            // установить состояние
            //
            обертка.setСостояние( Статусы_загрузки.ГОТОВО);
        }
        catch ( Exception ошибка){
            обертка.setСостояние( Статусы_загрузки.ГОТОВО);
        }
        return new AsyncResult<>(обертка);
    }

    public Future<Обертка> выгрузить_эксель( Integer идэ){
        Обертка обертка = new Обертка();
        обертка.setИдэ( идэ);
        обертка.setСостояние( Статусы_загрузки.РАБОТАЕТ);
        try {
            List<Section> секции = сервис.findAll();
            ByteOutputStream поток = транслятор.write( секции);
            обертка.setФайлик( поток);
            обертка.setСостояние( Статусы_загрузки.ГОТОВО);
        }
        catch ( Exception ошибка){
            обертка.setСостояние( Статусы_загрузки.ГОТОВО);
        }

        return new AsyncResult<>(обертка);
    }
}
