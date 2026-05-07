package br.com.takeshi.spring_boot_rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/test/v1")
public class TestLoggerController {

    private Logger logger = LoggerFactory.getLogger(TestLoggerController.class.getName());

    @GetMapping("/test")
    public String testLog(){
        logger.debug("This is a DEBUG log");
        logger.info("This is a INFO log");
        logger.warn("This is a WARN log");
        logger.error("This is a ERROR log");
        return "Logs generated successfully";
    }
}
