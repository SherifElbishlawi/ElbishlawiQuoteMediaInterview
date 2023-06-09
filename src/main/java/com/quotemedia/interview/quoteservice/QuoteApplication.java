package com.quotemedia.interview.quoteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class QuoteApplication {
    public static void main(final String[] args) {
        SpringApplication.run(QuoteApplication.class, args);
    }
}