package com.example.eventmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.example.eventmanagementsystem.model.Event;

@Entity
@Table(name = "sponsor")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int sponsorId;

    @Column(name = "name")
    private String sponsorName;

    @Column(name = "industry")
    private String industry;

    @ManyToMany
    @JoinTable(name = "event_sponsor", joinColumns = @JoinColumn(name = "sponsorid"), inverseJoinColumns = @JoinColumn(name = "eventid"))

    @JsonIgnoreProperties("sponsors")
    private List<Event> events;

    public Sponsor() {

    }

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int id) {
        this.sponsorId = id;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String name) {
        this.sponsorName = name;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}