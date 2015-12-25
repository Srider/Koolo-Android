package com.example.srravela.koolo.quotes.listeners;

import android.net.Uri;
import android.os.Bundle;
/**
 * Created by srravela on 11/10/2015.
 */

public interface KooloQuotesInteractionListener {
    public static final String KOOLO_QUOTES_ACTION="KOOLO_QUOTES_ACTION";
    public static final int KOOLO_NEW_QUOTE_ENTRY_DONE=0;
    public void onQuotesInteraction(Bundle urlBundle);
}
