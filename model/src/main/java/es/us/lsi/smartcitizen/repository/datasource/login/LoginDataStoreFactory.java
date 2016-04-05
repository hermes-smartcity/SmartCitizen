package es.us.lsi.smartcitizen.repository.datasource.login;

import javax.inject.Singleton;

@Singleton
public class LoginDataStoreFactory {

    public LoginDataStoreFactory(){

    }

    public LoginDataStore createGoogleLoginDataStore() {
        return new GoogleLoginDataStore();
    }
}
