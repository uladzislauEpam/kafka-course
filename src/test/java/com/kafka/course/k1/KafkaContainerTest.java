package com.kafka.course.k1;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@RunWith(SpringRunner.class)
@Import(KafkaContainerTest.KafkaContainerTestConfiguration.class)
@SpringBootTest(classes = KafkaApplication.class)
@DirtiesContext
public class KafkaContainerTest {

  @ClassRule
  public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

  @Autowired
  public KafkaTemplate<String, String> template;

  @Autowired
  private Consumer consumer;

  @Autowired
  private Producer producer;

  private String topic = "1";

  @Before
  public void setup() {
    consumer.resetLatch();
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaContainerTest.class);

  @Test
  public void givenKafkaDockerContainer_whenSendingWithDefaultTemplate_thenMessageReceived() throws Exception {
    LOGGER.info("KAFKA SERVER: " + kafka.getBootstrapServers());
    String data = "Sending with Template";
    template.send(topic, data);
    boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
    assertTrue(messageConsumed);
    assertThat(consumer.getPayload(), containsString(data));
  }

  @Test
  public void givenKafkaDockerContainer_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
    String data = "Sending with Producer";
    producer.send(topic, data);
    boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
    assertTrue(messageConsumed);
    assertThat(consumer.getPayload(), containsString(data));
  }

  @TestConfiguration
  static class KafkaContainerTestConfiguration {

    @Bean
    public Map<String, Object> consumerConfigs() {
      Map<String, Object> props = new HashMap<>();
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      return props;
    }

    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
      return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      return factory;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
      Map<String, Object> configProps = new HashMap<>();
      configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
      configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
      return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
      return new KafkaTemplate<>(producerFactory());
    }

  }

}
