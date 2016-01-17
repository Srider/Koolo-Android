package com.example.srravela.koolo.entities;

import java.io.Serializable;

/**
 * Created by srikar on 4/1/16.
 */
public class CalendarDates  implements Serializable {
    private String dateText;
    private String dayText;
    private String monthText;
    private Utils.ColorType colorType;

    public CalendarDates(String dateText, String dayText,String monthText, Utils.ColorType colorType) {
        this.dateText = dateText;
        this.dayText = dayText;
        this.monthText = monthText;
        this.colorType = colorType;
    }

    public  void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public  String getDateText() {
        return dateText;
    }

    public  void setDayText(String dayText) {
        this.dayText = dayText;
    }

    public  String getDayText() {
        return dayText;
    }

    public  void setMonthText(String dayText) {
        this.monthText = monthText;
    }

    public  String getMonthText() {
        return monthText;
    }


    public  void setColorType(Utils.ColorType colorType) {
        this.colorType = colorType;
    }

    public  Utils.ColorType getColorType() {
        return  colorType;

    }

}
