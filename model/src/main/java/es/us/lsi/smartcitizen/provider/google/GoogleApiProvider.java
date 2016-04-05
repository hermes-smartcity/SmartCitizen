package es.us.lsi.smartcitizen.provider.google;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class GoogleApiProvider {

    private final Context mContext;

    @Inject
    public GoogleApiProvider(Context context){
        this.mContext = context;
    }

    public Observable<GoogleSignInAccount> getSignInAccount(){
        return getGoogleSignInApiClientObservable()
                .flatMap(googleApiClient ->
                    Observable.create(new GoogleSignInAccountObservable(mContext,Auth.GoogleSignInApi.getSignInIntent(googleApiClient)))
                );
       /*getGoogleSignInApiClientObservable().doOnNext(new Action1<GoogleApiClient>() {
           @Override
           public void call(GoogleApiClient googleApiClient) {
               Log.e("HOLA","HOLAAAAAA");
               Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
               signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               mContext.startActivity(signInIntent);
           }
       });*/

    }

    public Observable<GoogleApiClient> getGoogleApiClientObservable(Api... apis) {
        return GoogleAPIClientObservable.create(this.mContext, apis);
    }

    public Observable<GoogleApiClient> getGoogleSignInApiClientObservable(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        return GoogleAPIClientObservable.create(this.mContext, Auth.GOOGLE_SIGN_IN_API,googleSignInOptions);
    }

    public static <T extends Result> Observable<T> fromPendingResult(PendingResult<T> result) {
        return Observable.create(new PendingResultObservable<>(result));
    }

}
