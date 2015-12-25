package com.example.srravela.koolo.checklists.listeners;

import com.example.srravela.koolo.entities.Checklist;

import java.util.List;

/**
 * Created by srravela on 11/30/2015.
 */
public interface ChecklistItemsUpdatedListener {
    public void onChecklistItemsUpdated(List<Checklist>items);
}
