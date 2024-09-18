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
public class SponsorJpaService implements SponsorRepository {
    @Autowired
    private EventJpaRepository eventJpaRepository;
    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Sponsor> getSponsors() {
        List<Sponsor> sponsorList = sponsorJpaRepository.findAll();
        ArrayList<Sponsor> sponsors = new ArrayList<>(sponsorList);
        return sponsors;
    }

    public Sponsor getSponsorById(int sponsorId) {
        try {
            Sponsor sp = sponsorJpaRepository.findById(sponsorId).get();
            return sp;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        try {
            List<Integer> Ids = new ArrayList<>();
            for (Event ep : sponsor.getEvents()) {
                Ids.add(ep.getEventId());
            }
            List<Event> eventList = eventJpaRepository.findAllById(Ids);
            for (int i : Ids) {
                if (!eventJpaRepository.existsById(i))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            sponsor.setEvents(eventList);
            for (Event ap : eventList) {
                ap.getSponsors().add(sponsor);
            }
            sponsorJpaRepository.save(sponsor);
            eventJpaRepository.saveAll(eventList);
            return sponsor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        try {
            Sponsor new_sponsor = sponsorJpaRepository.findById(sponsorId).get();
            if (sponsor.getSponsorName() != null) {
                new_sponsor.setSponsorName(sponsor.getSponsorName());
            }
            if (sponsor.getIndustry() != null) {
                new_sponsor.setIndustry(sponsor.getIndustry());
            }
            if (sponsor.getEvents() != null) {
                List<Integer> arr = new ArrayList<>();
                for (Event e : sponsor.getEvents()) {
                    arr.add(e.getEventId());
                }
                List<Event> events1 = eventJpaRepository.findAllById(arr);
                if (arr.size() != events1.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                List<Event> new_events = new ArrayList<>();
                for (Event event : sponsor.getEvents()) {
                    Event new_event = event;
                    if (event.getEventName() != null) {
                        new_event.setEventName(event.getEventName());
                    }
                    if (event.getDate() != null) {
                        new_event.setDate(event.getDate());
                    }
                    new_events.add(new_event);
                }
                eventJpaRepository.saveAll(new_events);
                new_sponsor.setEvents(new_events);
            }
            sponsorJpaRepository.save(new_sponsor);
            return new_sponsor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            List<Event> events = sponsor.getEvents();
            for (Event event : events) {
                event.getSponsors().remove(sponsor);
            }
            eventJpaRepository.saveAll(events);
            sponsorJpaRepository.deleteById(sponsorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Event> getSponsorEvents(int sponsorId) {
        Sponsor sponsor = getSponsorById(sponsorId);
        return sponsor.getEvents();
    }
}