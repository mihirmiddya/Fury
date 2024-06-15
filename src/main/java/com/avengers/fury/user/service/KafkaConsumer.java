package com.avengers.fury.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.avengers.fury.user.service.KafkaProducer.KAFKA_TOPIC;

@Service
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private static final String KAFKA_GROUP = "mihir_group";

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP)
    public void consume(String message) {
        logger.info("Consumed message: {}", message);
    }
}

