package it.italiancoders.kafkarequest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.italiancoders.kafkarequest.model.FibonacciRequest;
import it.italiancoders.kafkarequest.model.FibonacciResult;
import it.italiancoders.kafkarequest.service.FibonacciService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class FibonacciController {
    @Autowired
    FibonacciService fibonacciService;

    @PostMapping("/fibonacci")
    public ResponseEntity<?> postFibonacci(@RequestBody FibonacciRequest request) throws ExecutionException, InterruptedException, JsonProcessingException {
        FibonacciResult result = fibonacciService.calculate(request);
        return ResponseEntity.ok(result);
    }
}
