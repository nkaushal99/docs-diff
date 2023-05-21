package com.nikhil.service.document.server;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@EnableDiscoveryClient
@SpringBootApplication
public class DocumentApplication {

  public static void main(String[] args) {
    SpringApplication.run(DocumentApplication.class, args);
  }

  @Bean
  public NewTopic compare() {
    return TopicBuilder.name("compare-topic").build();
  }
}
