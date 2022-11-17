package com.kafka.course.k2.service;

import com.kafka.course.k2.Producer;
import com.kafka.course.k2.model.Reposition;
import com.kafka.course.k2.model.Signal;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
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
      LOGGER.error("+++++++++++ Validation failed ++++++++");
      e.printStackTrace();
    }
  }

  public void proceedVehicle(Signal signal, long offset) {
    Properties properties = new Properties();
    String bootstrapServers = "127.0.0.1:9092";
    String groupId = Integer.toString((int) (Math.random()*Integer.MAX_VALUE));
    String resetConfig = "earliest";
    String topic = "input";
    String autoCommit = "true";
    String commitInterval = "100";
    JsonDeserializer<Signal> deserializer = new JsonDeserializer<>(Signal.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    Signal signalPrevious = null;

    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, resetConfig);
    properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
    properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, commitInterval);


    KafkaConsumer<String, Signal> consumer = new KafkaConsumer<>(properties, new StringDeserializer(), new JsonDeserializer<>(Signal.class));
    consumer.subscribe(Collections.singletonList(topic));

      ConsumerRecords<String, Signal> records = consumer.poll(Duration.ofMillis(1000));
      for (ConsumerRecord<String, Signal> record : records) {
        if (record.offset() < offset && Objects.equals(record.value().getVehicleId(),
            signal.getVehicleId())) {
          signalPrevious = record.value();
        }
      }
    calculateAndSend(signalPrevious, signal);
  }

  private void calculateAndSend(Signal signal1, Signal signal2) {
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
