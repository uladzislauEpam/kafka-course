package com.kafka.course.k2;

import com.kafka.course.k2.model.Reposition;
import com.kafka.course.k2.model.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  @Autowired
  private KafkaTemplate<String, Signal> kafkaTemplateSig;

  @Autowired
  private KafkaTemplate<String, Reposition> kafkaTemplateRep;

  // 2. Producer: requirement “Messages from every vehicle must be processed sequentially!” Not implemented
  public void send(String topic, Signal signal) {
    kafkaTemplateSig.send(topic, signal.getVehicleId(), signal);
  }

  public void send(String topic, Reposition reposition) {
    kafkaTemplateRep.send(topic, reposition);
  }
}
