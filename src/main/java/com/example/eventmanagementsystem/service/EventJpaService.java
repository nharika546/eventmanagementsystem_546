package com.example.eventmanagementsystem.service;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;
import com.sun.xml.bind.annotation.OverrideAnnotationOf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class EventJpaService implements EventRepository {
    @Autowired
    private EventJpaRepository eventJpaRepository;
    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Event> getEvents() {
        List<Event> eventList = eventJpaRepository.findAll();
        ArrayList<Event> events = new ArrayList<>(eventList);
        return events;
    }

    @Override
    public Event getEventById(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            return event;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event addEvent(Event event) {
        try {
            List<Integer> Ids = new ArrayList<>();
            for (Sponsor sp : event.getSponsors()) {
                Ids.add(sp.getSponsorId());
            }
            List<Sponsor> sponsorList = sponsorJpaRepository.findAllById(Ids);
            if (Ids.size() != sponsorList.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            event.setSponsors(sponsorList);
            for (Sponsor sponsor : sponsorList) {
                sponsor.getEvents().add(event);
            }
            Event newEvent = eventJpaRepository.save(event);
            sponsorJpaRepository.saveAll(sponsorList);
            return newEvent;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        try {
            Event new_event = eventJpaRepository.findById(eventId).get();
            if (event.getEventName() != null) {
                new_event.setEventName(event.getEventName());
            }
            if (event.getDate() != null) {
                new_event.setDate(event.getDate());
            }
            if (event.getSponsors() != null) {
                List<Integer> arr = new ArrayList<>();
                for (Sponsor sp : event.getSponsors()) {
                    arr.add(sp.getSponsorId());
                }
                List<Sponsor> sponsor1 = sponsorJpaRepository.findAllById(arr);
                try {
                    for (int i : arr) {
                        Sponsor sp = sponsorJpaRepository.findById(i).get();
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                List<Sponsor> new_sponsors = new ArrayList<>();
                for (Sponsor sponsor : event.getSponsors()) {
                    Sponsor new_sponsor = sponsorJpaRepository.findById(sponsor.getSponsorId()).get();
                    if (sponsor.getSponsorName() != null) {
                        new_sponsor.setSponsorName(sponsor.getSponsorName());
                    }
                    if (sponsor.getIndustry() != null) {
                        new_sponsor.setIndustry(sponsor.getIndustry());
                    }
                    new_sponsors.add(new_sponsor);
                }
                sponsorJpaRepository.saveAll(new_sponsors);
                new_event.setSponsors(new_sponsors);
            }
            eventJpaRepository.save(new_event);
            return getEventById(eventId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteEvent(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            List<Sponsor> sponsors = event.getSponsors();
            for (Sponsor sponsor : sponsors) {
                sponsor.getEvents().remove(event);
            }
            sponsorJpaRepository.saveAll(sponsors);
            eventJpaRepository.deleteById(eventId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Sponsor> getEventSponsors(int eventId) {
        Event event = getEventById(eventId);
        return event.getSponsors();
    }
}