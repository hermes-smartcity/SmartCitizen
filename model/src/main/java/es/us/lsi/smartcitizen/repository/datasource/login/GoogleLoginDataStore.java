package es.us.lsi.smartcitizen.repository.datasource.login;


import es.us.lsi.smartcitizen.entity.UserEntity;
import rx.Observable;

public class GoogleLoginDataStore implements LoginDataStore {

    public GoogleLoginDataStore() {
    }

    @Override
    public Observable<UserEntity> userEntity() {

        //TODO
        return null;
    }
}
