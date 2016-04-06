package us.idinfor.smartcitizen.mvp.model.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.us.lsi.smartcitizen.entity.UserDomainEntity;
import us.idinfor.smartcitizen.mvp.model.UserViewEntity;

@Singleton
public class UserViewEntityDataMapper {

    @Inject
    public UserViewEntityDataMapper() {
    }

    public UserViewEntity transform(UserDomainEntity userDomainEntity) {
        UserViewEntity userViewEntity = null;
        if (userDomainEntity != null) {
            userViewEntity = new UserViewEntity(
                    userDomainEntity.getUserId(),
                    userDomainEntity.getEmail());
        }

        return userViewEntity;
    }
}