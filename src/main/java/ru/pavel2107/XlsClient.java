package ru.pavel2107;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.pavel2107.xls.config.Config;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//
// Starter for the app
//
@EnableSwagger2
@SpringBootApplication
@EnableAsync
public class XlsClient {

    Logger logger = LogManager.getLogger();

    private Config config;

    public XlsClient( Config config){
        this.config = config;
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.info( "delay=" + config.getPause() + " seconds");
        };
    }
    public static void main(String[] args){
        SpringApplication.run( XlsClient.class, args);
    }
}
