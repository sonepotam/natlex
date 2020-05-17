package ru.pavel2107.xls.фьютчерс;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Data;

@Data
public class Обертка {
    private Integer идэ;
    private String состояние;
    private ByteOutputStream файлик;
}
