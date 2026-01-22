package com.example.controller;

import com.example.model.SysinfoMessage;
import com.example.producer.NatsProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
public class EventReadingController {
    private final NatsProducer natsProducer;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventReadingController.class);

    @Autowired
    EventReadingController(
            NatsProducer natsProducer
    ) {
        this.natsProducer = natsProducer;
    }

    private static final Logger logger = LogManager.getLogger(EventReadingController.class);

    @PostMapping("/sysinfo")
    public ResponseEntity<?> postEventReading(@RequestBody SysinfoMessage sysinfoMessage) {
        logger.info("received message={}", sysinfoMessage.toString());
        try {
            natsProducer.publishSysinfoEvent(sysinfoMessage);
        } catch (JsonProcessingException jpe) {
            log.error(jpe.getMessage());
            return ResponseEntity.unprocessableEntity().body(jpe.getMessage());
        } catch (InterruptedException | TimeoutException e) {
            log.error("an exception occurred during processing of sysinfo message: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("unable to produce to NATS due to internal server err");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sysinfoMessage);
    }
}
