package com.avengers.fury.user.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.avengers.fury.user.service.KafkaProducer.KAFKA_TOPIC;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(KAFKA_TOPIC).build();
    }
}
