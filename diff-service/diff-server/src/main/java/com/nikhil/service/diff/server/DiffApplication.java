package com.nikhil.service.diff.server;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@EnableDiscoveryClient
@SpringBootApplication
public class DiffApplication {

  public static void main(String[] args) {
    SpringApplication.run(DiffApplication.class, args);
  }

  @Bean
  public NewTopic diff() {
    return TopicBuilder.name("diff-topic").build();
  }

}
