package es.us.lsi.smartcitizen.repository;


import javax.inject.Inject;

import es.us.lsi.smartcitizen.entity.mapper.LoginEntityDataMapper;
import es.us.lsi.smartcitizen.model.User;
import es.us.lsi.smartcitizen.repository.datasource.login.LoginDataStore;
import es.us.lsi.smartcitizen.repository.datasource.login.LoginDataStoreFactory;
import rx.Observable;

public class LoginDataRepository implements LoginRepository {

    private final LoginDataStoreFactory mLoginDataStoreFactory;
    private final LoginEntityDataMapper mLoginEntityDataMapper;

    @Inject
    public LoginDataRepository(LoginDataStoreFactory loginDataStoreFactory,
               LoginEntityDataMapper loginEntityDataMapper){
        this.mLoginDataStoreFactory = loginDataStoreFactory;
        this.mLoginEntityDataMapper = loginEntityDataMapper;
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Observable<User> googleLogin() {
        final LoginDataStore loginDataStore =
                this.mLoginDataStoreFactory.createGoogleLoginDataStore();
        return loginDataStore.userEntity()
                .map(userEntity -> this.mLoginEntityDataMapper.transform(userEntity));
    }
}
