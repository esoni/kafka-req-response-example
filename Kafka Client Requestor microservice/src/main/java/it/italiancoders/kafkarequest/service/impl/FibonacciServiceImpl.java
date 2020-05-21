package it.italiancoders.kafkarequest.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.italiancoders.kafkarequest.model.FibonacciRequest;
import it.italiancoders.kafkarequest.model.FibonacciResult;
import it.italiancoders.kafkarequest.service.FibonacciService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FibonacciServiceImpl implements FibonacciService {

    @Autowired
    ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${kafka.request.topic}")
    String requestTopic;

    @Value("${kafka.reply.topic}")
    String requestReplyTopic;

    @Override
    public FibonacciResult calculate(FibonacciRequest request) throws InterruptedException, ExecutionException, JsonProcessingException {
        log.info("send request fib [{]]", request.getNumber());
        String requestJSON = objectMapper.writeValueAsString(request);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, requestJSON);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
        RequestReplyFuture<String, String, String> sendAndReceive = kafkaTemplate.sendAndReceive(record);
        // confirm if producer produced successfully
        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
        log.info("ProducerRecord request[{}]", sendResult.getProducerRecord());
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" + header.value().toString()));
        // get consumer record
        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
        String jsonResponse = consumerRecord.value();

        // return consumer value
        return objectMapper.readValue(jsonResponse, FibonacciResult.class);
    }
}
