package com.demons.manager.backstage.consumer;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Outro
 * 将接收到的数据下发到kafka
 * '@RefreshScope'注解用于从nacos获取的kafka参数自动更新
 */
@Configuration
@RefreshScope
public class ManagerProducer {

  private static final Logger logger = LoggerFactory.getLogger(ManagerProducer.class);

  private static KafkaProducer<String, String> producer;

  @Value("${kafka.producer.key-serializer:org.apache.kafka.common.serialization.StringSerializer}")
  String keySerializer;
  @Value("${kafka.producer.value-serializer:org.apache.kafka.common.serialization.ByteArraySerializer}")
  String valueSerializer;
  @Value("${kafka.host}")
  String bootStrapServer;
  @Value("${kafka.producer.topic:manager_default_producer_topic}")
  String topic;
  @Value("${kafka.producer.request-message-size:10485760}")
  Integer requestMessageSize;

  public void initProducer() {
    logger.info("start init manager kafka producer...");
    final Properties properties = new Properties();
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
    properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, requestMessageSize);
    if (producer == null) {
      producer = new KafkaProducer<>(properties);
    }
    logger.info("Manager kafka producer init over, kafka props:{}", properties);
  }

  /**
   * producer是线程安全的,可以使用单例
   * @param data 需要发送的数据
   */
  public void sendData2Kafka(byte[] data) {
    try {
      logger.info("producer topic:{}", topic);
      logger.info("producer data:{}", new String(data, StandardCharsets.UTF_8));
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, new String(data, StandardCharsets.UTF_8));
      producer.send(record, new ProducerCallback(System.currentTimeMillis()));
    } catch (Exception e) {
      logger.error("Manager kafka producer send data catch exception!", e);
    }
  }
}

class ProducerCallback implements Callback {

  private static final Logger logger = LoggerFactory.getLogger(ProducerCallback.class);

  private final AtomicLong msgNum = new AtomicLong();
  private final long startTime;

  public ProducerCallback(long startTime) {
    this.startTime = startTime;
  }

  @Override
  public void onCompletion(RecordMetadata metadata, Exception exception) {
    final int maxSize = 500;
    if (exception != null) {
      logger.error("Error sending message to Kafka", exception);
      return;
    }
    msgNum.incrementAndGet();
    if (msgNum.get() % maxSize == 0) {
      logger.info("send to kafka msgNum :{}", msgNum.getAndSet(0));
    }
    if (logger.isDebugEnabled()) {
      long eventElapsedTime = System.currentTimeMillis() - startTime;
      if (metadata != null) {
        logger.debug("Ack message partition:{} offset:{}", metadata.partition(),
            metadata.offset());
      }
      logger.debug("Elapsed time for send: {}", eventElapsedTime);
    }
  }
}
