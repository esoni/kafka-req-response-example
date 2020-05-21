package it.italiancoders.kafkarequest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.italiancoders.kafkarequest.model.FibonacciRequest;
import it.italiancoders.kafkarequest.model.FibonacciResult;

import java.util.concurrent.ExecutionException;

public interface FibonacciService {
    FibonacciResult calculate(FibonacciRequest request) throws InterruptedException, ExecutionException, JsonProcessingException;
}
