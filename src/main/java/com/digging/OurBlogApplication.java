package com.digging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class OurBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(OurBlogApplication.class, args);
    }

}
