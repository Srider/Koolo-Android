package com.example.srravela.koolo.entities;

import java.io.Serializable;

/**
 * Created by srravela on 11/16/2015.
 */

public class Checklist implements Serializable{

    private String itemText;
    private Utils.StatusType statusType;

    public Checklist(String itemText, Utils.StatusType statusType) {
        this.itemText = itemText;
        this.statusType = statusType;
    }

    public  void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public  String getItemText() {
        return itemText;
    }

    public  void setStatusType(Utils.StatusType statusType) {
        this.statusType = statusType;
    }

    public  Utils.StatusType getStatusType() {
            return  statusType;

    }

}
