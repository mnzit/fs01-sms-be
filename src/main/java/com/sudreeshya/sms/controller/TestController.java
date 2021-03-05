package com.sudreeshya.sms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Profile({"aakash"})
@Slf4j
@Controller
public class TestController {
    public TestController() {
        log.debug("TestController instance created");
    }
}
