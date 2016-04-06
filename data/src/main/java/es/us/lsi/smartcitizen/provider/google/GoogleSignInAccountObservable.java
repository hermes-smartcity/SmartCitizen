package es.us.lsi.smartcitizen.provider.google;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.Subscriptions;

public class GoogleSignInAccountObservable implements Observable.OnSubscribe<GoogleSignInAccount> {

    private final Context mContext;
    private Intent mIntentResult;
    private boolean complete = false;

    public GoogleSignInAccountObservable(Context context, Intent result) {
        this.mContext = context;
        this.mIntentResult = result;
    }

    @Override
    public void call(Subscriber<? super GoogleSignInAccount> subscriber) {
        mIntentResult.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntentResult);
        GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(mIntentResult);
        if(googleSignInResult.isSuccess() && googleSignInResult.getSignInAccount() != null){
            subscriber.onNext(googleSignInResult.getSignInAccount());
            complete = true;
            subscriber.onCompleted();
        }
        subscriber.add(Subscriptions.create(() -> {
            if(!complete){
                subscriber.onError(new GoogleAPISignInException(
                        googleSignInResult.getStatus())
                );
            }
        }));
    }
}