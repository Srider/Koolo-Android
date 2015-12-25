package com.example.srravela.koolo.humor.listeners;

import android.os.Bundle;

/**
 * Created by srravela on 11/16/2015.
 */
public interface KooloHumourColorListener {

    public static final String KOOLO_HUMOUR_COLOR_ACTION="KOOLO_HUMOUR_COLOR_ACTION";
    public static final String ENTRY_HUMOUR_TEXT="ENTRY_HUMOUR_TEXT";

    /**
     * @param bundle
     */
    public void onHumourColorAction(Bundle bundle);
}
