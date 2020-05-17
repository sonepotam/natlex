package ru.pavel2107.xls.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value( "${app.pause}")
    @Getter  private int pause;
}
