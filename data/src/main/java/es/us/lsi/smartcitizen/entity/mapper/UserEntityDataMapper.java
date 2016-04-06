package es.us.lsi.smartcitizen.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.us.lsi.smartcitizen.entity.UserEntity;
import es.us.lsi.smartcitizen.entity.UserDomainEntity;

@Singleton
public class UserEntityDataMapper {

    @Inject
    public UserEntityDataMapper() {
    }

    public UserDomainEntity transform(UserEntity userEntity) {
        UserDomainEntity userDomainEntity = null;
        if (userEntity != null) {
            userDomainEntity = new UserDomainEntity(
                    userEntity.getUserId(),
                    userEntity.getEmail());
        }

        return userDomainEntity;
    }
}