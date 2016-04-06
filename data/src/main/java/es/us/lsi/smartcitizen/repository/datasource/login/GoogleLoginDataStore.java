package es.us.lsi.smartcitizen.repository.datasource.login;


import android.content.Context;

import es.us.lsi.smartcitizen.entity.UserEntity;
import es.us.lsi.smartcitizen.provider.google.GoogleApiProvider;
import rx.Observable;

public class GoogleLoginDataStore implements LoginDataStore {

    private final GoogleApiProvider mGoogleApiProvider;

    private final Context mContext;

    public GoogleLoginDataStore(Context context) {
        this.mContext = context.getApplicationContext();
        this.mGoogleApiProvider = new GoogleApiProvider(this.mContext);
    }

    @Override
    public Observable<UserEntity> userEntity() {
        return mGoogleApiProvider.getSignInAccount().flatMap(googleSignInAccount ->
            Observable.create(subscriber -> {
                UserEntity userEntity = new UserEntity(-1,googleSignInAccount.getEmail());
                subscriber.onNext(userEntity);
            })
        );
    }
}
