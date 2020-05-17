package ru.pavel2107.xls.service;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import ru.pavel2107.xls.domain.Section;
import java.io.InputStream;
import java.util.List;

public interface TranslatorService {
     List<Section> read(InputStream stream) throws Exception;
     ByteOutputStream write(List <Section> sections) throws Exception;
}
