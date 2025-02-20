package com.example.eventmanagementsystem.repository;

import java.util.*;

import com.example.eventmanagementsystem.model.*;

public interface SponsorRepository {
    ArrayList<Sponsor> getSponsors();

    Sponsor getSponsorById(int sponsorId);

    Sponsor addSponsor(Sponsor sponsor);

    Sponsor updateSponsor(int sponsorId, Sponsor sponsor);

    void deleteSponsor(int sponsorId);

    List<Event> getSponsorEvents(int sponsorId);
}