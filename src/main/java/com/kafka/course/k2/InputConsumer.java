package com.kafka.course.k2;

import com.kafka.course.k2.model.Signal;
import com.kafka.course.k2.service.ProducerService;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InputConsumer {

  @Autowired
  ProducerService producerService;

  @KafkaListener(topics = "input", groupId = "group1", containerFactory = "kafkaListenerContainerFactorySig")
  public void receiveListener1(ConsumerRecord<?, Signal> consumerRecord) {
    producerService.proceedVehicle(consumerRecord.value(), consumerRecord.offset());
  }

  @KafkaListener(topics = "input", groupId = "group1", containerFactory = "kafkaListenerContainerFactorySig")
  public void receiveListener2(ConsumerRecord<?, Signal> consumerRecord) {
    producerService.proceedVehicle(consumerRecord.value(), consumerRecord.offset());
  }

  @KafkaListener(topics = "input", groupId = "group1", containerFactory = "kafkaListenerContainerFactorySig")
  public void receiveListener3(ConsumerRecord<?, Signal> consumerRecord) {
    producerService.proceedVehicle(consumerRecord.value(), consumerRecord.offset());
  }

}
