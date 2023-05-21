package com.nikhil.service.highlight.server;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@EnableDiscoveryClient
@SpringBootApplication
public class HighlightApplication {

  public static void main(String[] args) {
    SpringApplication.run(HighlightApplication.class, args);
  }

  @Bean
  public NewTopic highlight() {
    return TopicBuilder.name("highlight-topic").build();
  }

}
