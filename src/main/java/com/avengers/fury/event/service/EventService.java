package com.avengers.fury.event.service;

import com.avengers.fury.event.model.Event;
import com.avengers.fury.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private EventRepository eventRepository;

    public void createEvent(String type, String data) {
        Event event = new Event();
        event.setType(type);
        event.setData(data);
        event.setCreatedOn(LocalDateTime.now());
        eventRepository.save(event);
        producer.sendMessage(String.valueOf(event.getId()));
    }


}
