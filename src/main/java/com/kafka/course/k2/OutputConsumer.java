package com.kafka.course.k2;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OutputConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(OutputConsumer.class);

  @Getter
  private CountDownLatch latch = new CountDownLatch(1);

  @Getter
  private String payload;

  @KafkaListener(topics = "output")
  public void receiveListener1(ConsumerRecord<?, ?> consumerRecord) {
    LOGGER.info("================= New output: {}", consumerRecord.value().toString());
    payload = consumerRecord.value().toString();
    latch.countDown();
  }

}
