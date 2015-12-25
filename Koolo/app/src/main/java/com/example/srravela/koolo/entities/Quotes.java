package com.example.srravela.koolo.entities;

import java.io.Serializable;

/**
 * Created by srravela on 11/17/2015.
 */
public class Quotes implements Serializable{

    private String quoteText;
    private boolean isSelected;

    public Quotes(String quoteText, boolean isSelected) {
        this.quoteText = quoteText;
        this.isSelected = isSelected;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
