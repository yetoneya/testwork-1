package ru.elena.zh.testwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.apache.log4j.PropertyConfigurator;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    private final static String PROP = "src/main/resources/log4j.properties";

    static {
        PropertyConfigurator.configure(PROP);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
        //Console.main(args);
    }
}
