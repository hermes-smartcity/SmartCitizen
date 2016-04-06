package es.us.lsi.smartcitizen.provider.google;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import rx.Observable;
import rx.Observer;

public class GoogleAPIClientObservable extends BaseObservable<GoogleApiClient> {


    public static Observable<GoogleApiClient> create(Context context, Api<? extends Api.ApiOptions.NotRequiredOptions>[] apis) {
        return Observable.create(new GoogleAPIClientObservable(context, apis));
    }

    public static Observable<GoogleApiClient> create(Context context, Api<GoogleSignInOptions> apiSignIn, GoogleSignInOptions options) {
        return Observable.create(new GoogleAPIClientObservable(context, apiSignIn, options));
    }

    protected GoogleAPIClientObservable(Context context, Api<? extends Api.ApiOptions.NotRequiredOptions>[] apis) {
        super(context, apis);
    }

    public GoogleAPIClientObservable(Context context, Api<GoogleSignInOptions> apiSignIn, GoogleSignInOptions options) {
        super(context, apiSignIn, options);
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, Observer<? super GoogleApiClient> observer) {
        observer.onNext(apiClient);
        observer.onCompleted();
    }
}