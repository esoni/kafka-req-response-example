package it.italiancoders.kafkarequest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FibonacciRequest {
    private Integer number;
}
