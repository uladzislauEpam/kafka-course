package com.kafka.course.k2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, Object object) {
    kafkaTemplate.send(topic, object);
  }
}
