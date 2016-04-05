package es.us.lsi.smartcitizen.repository;


import es.us.lsi.smartcitizen.model.User;
import rx.Observable;

public interface LoginRepository {

    Observable<User> googleLogin();
}
