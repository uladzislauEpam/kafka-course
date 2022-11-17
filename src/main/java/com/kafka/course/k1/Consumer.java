package com.kafka.course.k1;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

  @Getter
  private CountDownLatch latch = new CountDownLatch(1);

  @Getter
  private String payload;

  @KafkaListener(topics = "1")
  public void receive(ConsumerRecord<?, ?> consumerRecord) {
    LOGGER.info("================= Message received: {}", consumerRecord.value().toString());
    payload = consumerRecord.value().toString();
    latch.countDown();
  }

}
