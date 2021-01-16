package com.demons.manager.nacos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Outro
 * nacos配置参数绑定实体类,可自动更新
 */
@EnableAutoConfiguration
@Configuration
@RefreshScope
public class NacosConfigConfiguration {

  @Value(value = "${netty.port:1218}")
  Integer nettyPort;
  @Value(value = "${netty.boss-number:1}")
  Integer bossNumber;
  @Value(value = "${netty.worker-number:4}")
  Integer workerNumber;
  @Value(value = "${netty.timeout-sec:60}")
  Integer timeoutSec;
  @Value(value = "${kafka.host}")
  String kafkaHost;
  @Value(value = "${kafka.consumer.topic:default_consumer_topic}")
  String consumerTopic;
  @Value(value = "${kafka.consumer.group:default_consumer_group}")
  String consumerGroup;
  @Value(value = "${kafka.producer.topic:default_producer_topic}")
  String producerTopic;

  public Integer getNettyPort() {
    return nettyPort;
  }

  public Integer getBossNumber() {
    return bossNumber;
  }

  public Integer getWorkerNumber() {
    return workerNumber;
  }

  public Integer getTimeoutSec() {
    return timeoutSec;
  }

  public String getKafkaHost() {
    return kafkaHost;
  }

  public String getConsumerTopic() {
    return consumerTopic;
  }

  public String getConsumerGroup() {
    return consumerGroup;
  }

  public String getProducerTopic() {
    return producerTopic;
  }

  @Override
  public String toString() {
    // 注:这里不能使用JSON.toJSONString(this),会报错无法使用
    return "NacosConfigConfiguration{" +
        "nettyPort=" + nettyPort +
        ", bossNumber=" + bossNumber +
        ", workerNumber=" + workerNumber +
        ", timeoutSec=" + timeoutSec +
        ", kafkaHost='" + kafkaHost + '\'' +
        ", consumerTopic='" + consumerTopic + '\'' +
        ", consumerGroup='" + consumerGroup + '\'' +
        ", producerTopic='" + producerTopic + '\'' +
        '}';
  }
}
