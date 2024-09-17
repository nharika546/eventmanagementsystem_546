package com.example.eventmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.*;
import com.example.eventmanagementsystem.model.Sponsor;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int eventId;

    @Column(name = "name")
    private String eventName;

    @Column(name = "date")
    private String date;

    @ManyToMany(mappedBy = "events")
    @JsonIgnoreProperties("events")
    private List<Sponsor> sponsors;

    public Event() {

    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public List<Sponsor> getSponsors() {
        return this.sponsors;
    }

    public void setEventId(int id) {
        this.eventId = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}