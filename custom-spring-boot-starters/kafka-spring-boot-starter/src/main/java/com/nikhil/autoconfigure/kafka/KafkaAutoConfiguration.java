package com.nikhil.autoconfigure.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@PropertySource({"classpath:kafka.properties"})
@Configuration
@EnableKafka
public class KafkaAutoConfiguration {
}
