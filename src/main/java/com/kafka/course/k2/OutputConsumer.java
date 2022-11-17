package com.kafka.course.k2;

import com.kafka.course.k2.model.Reposition;
import com.kafka.course.k2.model.Signal;
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

  @KafkaListener(topics = "output", groupId = "group2", containerFactory = "kafkaListenerContainerFactoryRep")
  public void receiveListener3(ConsumerRecord<?, Reposition> consumerRecord) {
    LOGGER.info("================= New output: {}", consumerRecord.value().toString());
  }

}
