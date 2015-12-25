package com.example.srravela.koolo.passcode.listeners;

import android.os.Bundle;

/**
 * Created by srravela on 11/30/2015.
 */
public interface KooloPasscodeVerificationListener {
    public static final String KOOLO_PASSCODE_VERIFICATION="KOOLO_PASSCODE_VERIFICATION";
    public static final int KOOLO_PASSCODE_ENTERED_ACTION=0;
    public static final int KOOLO_PASSCODE_RETRY_EXPIRED=1;
    public static final int KOOLO_CORRECT_SECURITY_ANSWER_ENTERED = 2;

    public void onPasscodeVerification(Bundle urlBundle);
}