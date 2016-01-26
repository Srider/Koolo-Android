package com.example.srravela.koolo.entities;

import java.io.Serializable;

/**
 * Created by srikar on 4/1/16.
 */
public class CalendarEvents  implements Serializable {

    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventType;
    private String eventMonth;
    private boolean isTough;
    private boolean isLong;
    private boolean isFaith;
    private boolean isRemindMe;
    private Utils.ColorType colorType;

    public CalendarEvents(String eventName, String eventDate, String eventTime, String eventType, boolean isTough, boolean isLong,boolean isFaith, boolean isRemindMe, Utils.ColorType colorType) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.eventMonth = eventMonth;
        this.isTough = isTough;
        this.isLong = isLong;
        this.isFaith = isFaith;
        this.isRemindMe = isRemindMe;
        this.colorType = colorType;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }


    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isTough() {
        return isTough;
    }

    public void setIsTough(boolean isTough) {
        this.isTough = isTough;
    }


    public boolean isLong() {
        return isLong;
    }

    public void setIsLong(boolean isLong) {
        this.isLong = isLong;
    }

    public boolean isFaith() {
        return isFaith;
    }

    public void setIsFaith(boolean isFaith) {
        this.isFaith = isFaith;
    }


    public boolean isRemindMe() {
        return isRemindMe;
    }

    public void setIsRemindMe(boolean isRemindMe) {
        this.isRemindMe = isRemindMe;
    }



    public Utils.ColorType getColorType() {
        return colorType;
    }

    public void setColorType(Utils.ColorType colorType) {
        this.colorType = colorType;
    }




}
