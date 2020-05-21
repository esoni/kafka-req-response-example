package it.italiancoders.kafkaconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.italiancoders.kafkaconsumer.model.FibonacciRequest;
import it.italiancoders.kafkaconsumer.model.FibonacciResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static long fibonacci(int i) {
        /* F(i) non e` definito per interi i negativi! */
        if (i == 0) return 0;
        else if (i == 1) return 1;
        else return fibonacci(i-1) + fibonacci(i-2);
    }

    @KafkaListener(topics = {"${kafka.request.topic}"})
    @SendTo
    public String onMessage(String req) throws JsonProcessingException {
        FibonacciRequest fibonacciRequest = objectMapper.readValue(req, FibonacciRequest.class);
        long result = fibonacci(fibonacciRequest.getNumber());
        FibonacciResult calc = new FibonacciResult();
        calc.setResult(result);
        return objectMapper.writeValueAsString(calc);
    }
}
