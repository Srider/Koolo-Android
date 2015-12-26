package com.example.srravela.koolo.passcode.listeners;

import android.os.Bundle;

/**
 * Created by srravela on 11/17/2015.
 */
public interface KooloPasscodeInteractionListener {
    public static final String KOOLO_PASSCODE_ACTION="KOOLO_PASSCODE_ACTION";
    public static final int KOOLO_PASSCODE_ENABLED_ACTION=0;
    public static final int KOOLO_PASSCODE_SECURITY_QUESTION_CONFIGURED=1;

    public static final int KOOLO_ENTER_PASSCODE_BUTTON_CLICKED_ACTION = 2;
    public static final int KOOLO_PASSCODE_RETRY_EXPIRED_ACTION = 3;
    public static final int KOOLO_PASSCODE_SET_ACTION = 4;


    public void onPasscodeInteraction(Bundle urlBundle);
}
