package com.sudreeshya.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
        printBanner();
    }

    public static void printBanner() {
        log.info("\n  #####  #     #  #####  \n" +
                " #     # ##   ## #     # \n" +
                " #       # # # # #       \n" +
                "  #####  #  #  #  #####  \n" +
                "       # #     #       # \n" +
                " #     # #     # #     # \n" +
                "  #####  #     #  #####  ");
    }

}
