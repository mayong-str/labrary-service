package cn.deyuan.librarydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"cn.deyuan.librarydemo","cn.deyuan.plc4xmc"})
public class LibrarydemoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrarydemoServiceApplication.class, args);
    }

}
