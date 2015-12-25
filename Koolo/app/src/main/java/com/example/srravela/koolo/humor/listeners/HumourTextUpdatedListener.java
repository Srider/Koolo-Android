package com.example.srravela.koolo.humor.listeners;

import com.example.srravela.koolo.entities.Humour;

import java.util.List;

/**
 * Created by srravela on 11/19/2015.
 */
public interface HumourTextUpdatedListener {
    public void onHumourTextUpdated(List<Humour> humourList);
}
