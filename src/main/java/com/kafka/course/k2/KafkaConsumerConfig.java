package com.kafka.course.k2;

import com.kafka.course.k2.model.Reposition;
import com.kafka.course.k2.model.Signal;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${bootstrapServers}")
  String bootstrapServers;

    @Bean
    public ConsumerFactory<String, Signal> consumerFactorySig() {

      JsonDeserializer<Signal> deserializer = new JsonDeserializer<>(Signal.class);
      deserializer.setRemoveTypeHeaders(false);
      deserializer.addTrustedPackages("*");
      deserializer.setUseTypeMapperForKey(true);

      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10000");
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
      props.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
          StringDeserializer.class);
      props.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
          deserializer);
      return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Signal> kafkaListenerContainerFactorySig() {
      ConcurrentKafkaListenerContainerFactory<String, Signal> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactorySig());
      return factory;
    }

  @Bean
  public ConsumerFactory<String, Reposition> consumerFactoryRep() {

    JsonDeserializer<Reposition> deserializer = new JsonDeserializer<>(Reposition.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10000");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        deserializer);
    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Reposition> kafkaListenerContainerFactoryRep() {
    ConcurrentKafkaListenerContainerFactory<String, Reposition> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactoryRep());
    return factory;
  }
}