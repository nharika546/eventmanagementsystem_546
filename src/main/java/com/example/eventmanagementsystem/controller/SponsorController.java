package com.example.eventmanagementsystem.controller;

import com.example.eventmanagementsystem.service.EventJpaService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.eventmanagementsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import com.example.eventmanagementsystem.service.*;

@RestController
public class SponsorController {
    @Autowired
    public SponsorJpaService sponsorService;

    @GetMapping("/events/sponsors")
    public List<Sponsor> getSponsors() {
        return sponsorService.getSponsors();
    }

    @GetMapping("/events/sponsors/{sponsorId}")
    public Sponsor getSponsorById(@PathVariable("sponsorId") int sponsorId) {
        return sponsorService.getSponsorById(sponsorId);
    }

    @PostMapping("events/sponsors")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return sponsorService.addSponsor(sponsor);
    }

    @PutMapping("/events/sponsors/{sponsorId}")
    public Sponsor updateSponsor(@PathVariable("sponsorId") int sponsorId, @RequestBody Sponsor sponsor) {
        return sponsorService.updateSponsor(sponsorId, sponsor);
    }

    @DeleteMapping("/events/sponsors/{sponsorId}")
    public void deleteSponsor(@PathVariable("sponsorId") int sponsorId) {
        sponsorService.deleteSponsor(sponsorId);
    }
}