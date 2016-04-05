package us.idinfor.smartcitizen.di.modules;

import dagger.Module;

@Module
public class UserModule {

    private int mUserId = -1;

    public UserModule(){}

    public UserModule(int userId){
        this.mUserId = userId;
    }

    /*@Provides
    @PerActivity
    @Named("user_details") UseCase provideGetUserDetailsUseCase(
            UserRepository userRepository,
            @Named("executor_thread")Scheduler executorThread,
            @Named("ui_thread") Scheduler uiThread){
        return new GetUserDetails(userId, userRepository, executorThread, uiThread);
    }*/

}
