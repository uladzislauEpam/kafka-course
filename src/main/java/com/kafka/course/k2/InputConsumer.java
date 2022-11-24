package com.kafka.course.k2;

import com.kafka.course.k2.model.Signal;
import com.kafka.course.k2.service.ProducerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
// 7. Better to use 1 @KafkaListener annotated method
public class InputConsumer {

  @Autowired
  ProducerService producerService;

  @KafkaListener(topics = "input", groupId = "group1",
      containerFactory = "kafkaListenerContainerFactorySig", concurrency = "3")
  public void receiveListener(ConsumerRecord<?, Signal> consumerRecord) {
    producerService.proceedVehicle(consumerRecord.value());
  }

}
