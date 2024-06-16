package com.avengers.fury.event.repository;

import com.avengers.fury.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
