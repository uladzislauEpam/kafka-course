package com.kafka.course.k1Standalone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class Consumer {

  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(Consumer.class);
    Properties properties = new Properties();
    String bootstrapServers = "127.0.0.1:9092";
    String groupId = "group1";
    String resetConfig = "earliest";
    String topic = "1";
    String autoCommit = "true";
    String commitInterval = "100";

    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, resetConfig);
    properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
    properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, commitInterval);


    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(Collections.singletonList(topic));

    while (true) {
      ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(1000));
      for (ConsumerRecord<String,String> record : records) {
        logger.info(" ============================= Data received ==============================");
        logger.info(" Key: " + record.key() + " Value " + record.value());
        logger.info(" Partition: " + record.partition() + " Offset " + record.offset());
      }
    }
  }

}
