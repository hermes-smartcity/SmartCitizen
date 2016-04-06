package es.us.lsi.smartcitizen.repository;


import es.us.lsi.smartcitizen.entity.UserDomainEntity;
import rx.Observable;

public interface LoginRepository {

    Observable<UserDomainEntity> googleLogin();
}
