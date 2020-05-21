package it.italiancoders.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaconsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaconsumerApplication.class, args);
    }

}
