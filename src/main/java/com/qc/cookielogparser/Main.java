package com.qc.cookielogparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootApplication class to bootstrap the application.
 */
@SpringBootApplication
public class Main
{
    /**
     * Spring boot main bootstrap method.
     * @param args
     */
    public static void main(String[] args)
    {
        System.exit(SpringApplication.exit(SpringApplication.run(Main.class, args)));
    }
}
