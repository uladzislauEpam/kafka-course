package com.kafka.course.k2.service;

import com.kafka.course.k2.Producer;
import com.kafka.course.k2.model.Reposition;
import com.kafka.course.k2.model.Signal;
import java.util.Objects;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

  @Autowired
  private Producer producer;

  private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

  public void validateAndSend(Signal signal) {
    try {
      validate(signal.getVehicleId());
      validate(signal.getX());
      validate(signal.getY());
      producer.send("input", signal);
    } catch (Exception e) {
      LOGGER.error("Validation failed");
    }
  }

  public void calculateAndSend(Signal signal1, Signal signal2) {
    try {
      validateSame(signal1, signal2);
      Reposition reposition = Reposition.builder()
          .vehicleId(signal1.getVehicleId())
          .travelledY(signal2.getY() - signal1.getY())
          .travelledX(signal2.getX() - signal1.getX())
          .build();
      producer.send("output", reposition);
    } catch (Exception e) {
    LOGGER.error("Incomparable objects");
    }
  }

  private void validate(Object o) throws Exception {
    if (Objects.isNull(o)) {
      throw new Exception();
    }
  }

  private void validateSame(Signal s1, Signal s2) throws Exception {
    if (!Objects.equals(s1.getVehicleId(), s2.getVehicleId())) {
      throw new Exception();
    }
  }

}
