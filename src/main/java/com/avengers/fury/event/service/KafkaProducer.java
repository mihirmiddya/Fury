package com.avengers.fury.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    public static final String KAFKA_TOPIC = "mihir_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send(KAFKA_TOPIC, message);
        logger.info("Message sent to Kafka topic message {}", message);
    }
}
