package es.us.lsi.smartcitizen.provider.google;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

public abstract class BaseObservable<T> implements Observable.OnSubscribe<T> {

    private final Context mContext;
    private List<Api<? extends Api.ApiOptions.NotRequiredOptions>> mServices;
    private Api<GoogleSignInOptions> mSignInApi;
    private GoogleSignInOptions mSignInOptions;

    protected BaseObservable(Context context, Api<? extends Api.ApiOptions.NotRequiredOptions>[] services) {
        this.mContext = context;
        this.mServices = Arrays.asList(services);
    }

    public BaseObservable(Context context, Api<GoogleSignInOptions> apiSignIn, GoogleSignInOptions options) {
        this.mContext = context;
        this.mSignInApi = apiSignIn;
        this.mSignInOptions = options;
    }


    @Override
    public void call(Subscriber<? super T> subscriber) {

        final GoogleApiClient apiClient = createApiClient(subscriber);
        try {
            apiClient.connect();
        } catch (Throwable ex) {
            subscriber.onError(ex);
        }

        subscriber.add(Subscriptions.create(() -> {
            if (apiClient.isConnected() || apiClient.isConnecting()) {
                onUnsubscribed(apiClient);
                apiClient.disconnect();
            }
        }));
    }


    protected GoogleApiClient createApiClient(Subscriber<? super T> subscriber) {

        ApiClientConnectionCallbacks apiClientConnectionCallbacks = new ApiClientConnectionCallbacks(subscriber);

        GoogleApiClient.Builder apiClientBuilder = new GoogleApiClient.Builder(mContext);

        if(mServices != null && !mServices.isEmpty()){
            for (Api<? extends Api.ApiOptions.NotRequiredOptions> service : mServices) {
                apiClientBuilder.addApi(service);
            }
        }else if(mSignInApi != null && mSignInOptions != null){
            apiClientBuilder.addApi(mSignInApi,mSignInOptions);
        }

        apiClientBuilder.addConnectionCallbacks(apiClientConnectionCallbacks);
        apiClientBuilder.addOnConnectionFailedListener(apiClientConnectionCallbacks);

        GoogleApiClient apiClient = apiClientBuilder.build();

        apiClientConnectionCallbacks.setClient(apiClient);

        return apiClient;

    }

    protected void onUnsubscribed(GoogleApiClient googleApiClient) {
    }

    protected abstract void onGoogleApiClientReady(GoogleApiClient apiClient, Observer<? super T> observer);

    private class ApiClientConnectionCallbacks implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

        final private Observer<? super T> observer;

        private GoogleApiClient apiClient;

        private ApiClientConnectionCallbacks(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onConnected(Bundle bundle) {
            try {
                onGoogleApiClientReady(apiClient, observer);
            } catch (Throwable ex) {
                observer.onError(ex);
            }
        }

        @Override
        public void onConnectionSuspended(int cause) {
            observer.onError(new GoogleAPIConnectionSuspendedException(cause));
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            observer.onError(new GoogleAPIConnectionException("Error connecting to GoogleApiClient.", connectionResult));
        }

        public void setClient(GoogleApiClient client) {
            this.apiClient = client;
        }
    }

}