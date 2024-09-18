package com.example.eventmanagementsystem.repository;

import java.util.*;

import com.example.eventmanagementsystem.model.*;

public interface EventRepository {
    ArrayList<Event> getEvents();

    Event getEventById(int eventId);

    Event addEvent(Event event);

    Event updateEvent(int eventId, Event event);

    void deleteEvent(int eventId);

    List<Sponsor> getEventSponsors(int eventId);
}