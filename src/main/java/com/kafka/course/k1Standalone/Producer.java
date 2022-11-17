package com.kafka.course.k1Standalone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {

  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(Producer.class);
    Properties properties = new Properties();
    String bootstrapServers = "127.0.0.1:9092";
    String topic = "1";
    String message = "UL";

    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, "id_1", message);

    producer.send(producerRecord, (recordMetadata, e) -> {
      if (e != null) {
        logger.info(" ====================================== Exception in topic " + recordMetadata.topic() + ": " + e.getMessage());
      } else {
        logger.info(" ====================================== Posted to " + recordMetadata.topic());
      }
    });

    producer.flush();
    producer.close();
  }

}
