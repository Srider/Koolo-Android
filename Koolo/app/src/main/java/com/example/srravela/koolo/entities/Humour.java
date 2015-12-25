package com.example.srravela.koolo.entities;

import java.io.Serializable;

/**
 * Created by srravela on 11/16/2015.
 */
public class Humour implements Serializable{

    private String humourText;
    private Utils.ColorType colorType;

    public Humour(String humourText, Utils.ColorType colorType) {
        this.humourText = humourText;
        this.colorType = colorType;
    }

    public void setHumourText(String humourText) {
        this.humourText = humourText;
    }

    public String getHumourText() {
        return humourText;
    }

    public void setColorType(Utils.ColorType colorType) {
        this.colorType = colorType;
    }

    public Utils.ColorType getColorType() {
        return colorType;
    }

}
