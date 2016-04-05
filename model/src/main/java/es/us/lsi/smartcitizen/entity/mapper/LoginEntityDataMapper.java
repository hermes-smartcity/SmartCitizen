package es.us.lsi.smartcitizen.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.us.lsi.smartcitizen.entity.UserEntity;
import es.us.lsi.smartcitizen.model.User;

@Singleton
public class LoginEntityDataMapper {

    @Inject
    public LoginEntityDataMapper() {
    }

    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User(
                    userEntity.getUserId(),
                    userEntity.getEmail());
        }

        return user;
    }
}