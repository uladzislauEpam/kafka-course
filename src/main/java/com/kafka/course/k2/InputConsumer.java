package com.kafka.course.k2;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InputConsumer {

  @Getter
  private CountDownLatch latch1 = new CountDownLatch(1);

  @Getter
  private CountDownLatch latch2 = new CountDownLatch(1);

  @Getter
  private CountDownLatch latch3 = new CountDownLatch(1);

  @Getter
  private String payload1;

  @Getter
  private String payload2;

  @Getter
  private String payload3;

  @KafkaListener(topics = "input")
  public void receiveListener1(ConsumerRecord<?, ?> consumerRecord) {
    payload1 = consumerRecord.value().toString();
    latch1.countDown();
  }

  @KafkaListener(topics = "input")
  public void receiveListener2(ConsumerRecord<?, ?> consumerRecord) {
    payload2 = consumerRecord.value().toString();
    latch2.countDown();
  }

  @KafkaListener(topics = "input")
  public void receiveListener3(ConsumerRecord<?, ?> consumerRecord) {
    payload3 = consumerRecord.value().toString();
    latch3.countDown();
  }

}
