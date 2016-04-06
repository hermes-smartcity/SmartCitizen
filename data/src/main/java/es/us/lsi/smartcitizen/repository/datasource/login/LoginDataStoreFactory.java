package es.us.lsi.smartcitizen.repository.datasource.login;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginDataStoreFactory {

    private final Context mContext;

    @Inject
    public LoginDataStoreFactory(Context context){
        this.mContext = context.getApplicationContext();
    }

    public LoginDataStore createGoogleLoginDataStore() {
        return new GoogleLoginDataStore(this.mContext);
    }
}
