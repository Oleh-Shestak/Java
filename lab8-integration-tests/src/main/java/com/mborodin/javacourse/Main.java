package com.mborodin.javacourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mborodin.javacourse")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}