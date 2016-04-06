package es.us.lsi.smartcitizen.repository;


import javax.inject.Inject;

import es.us.lsi.smartcitizen.entity.mapper.UserEntityDataMapper;
import es.us.lsi.smartcitizen.entity.UserDomainEntity;
import es.us.lsi.smartcitizen.repository.datasource.login.LoginDataStore;
import es.us.lsi.smartcitizen.repository.datasource.login.LoginDataStoreFactory;
import rx.Observable;

public class LoginDataRepository implements LoginRepository {

    private final LoginDataStoreFactory mLoginDataStoreFactory;
    private final UserEntityDataMapper mUserEntityDataMapper;

    @Inject
    public LoginDataRepository(LoginDataStoreFactory loginDataStoreFactory,
               UserEntityDataMapper userEntityDataMapper){
        this.mLoginDataStoreFactory = loginDataStoreFactory;
        this.mUserEntityDataMapper = userEntityDataMapper;
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Observable<UserDomainEntity> googleLogin() {
        final LoginDataStore loginDataStore =
                this.mLoginDataStoreFactory.createGoogleLoginDataStore();
        return loginDataStore.userEntity()
                .map(userEntity -> this.mUserEntityDataMapper.transform(userEntity));
    }
}
