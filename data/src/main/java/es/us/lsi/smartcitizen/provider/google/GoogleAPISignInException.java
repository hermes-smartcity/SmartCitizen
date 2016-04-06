package es.us.lsi.smartcitizen.provider.google;

import com.google.android.gms.common.api.Status;

public class GoogleAPISignInException extends RuntimeException {
    private final Status mStatus;

    GoogleAPISignInException(Status status) {
        this.mStatus = status;
    }

    public Status getStatus() {
        return mStatus;
    }
}