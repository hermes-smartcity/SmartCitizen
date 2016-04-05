package es.us.lsi.smartcitizen.repository.datasource.login;

import es.us.lsi.smartcitizen.entity.UserEntity;
import rx.Observable;

public interface LoginDataStore {

    Observable<UserEntity> userEntity();

}
