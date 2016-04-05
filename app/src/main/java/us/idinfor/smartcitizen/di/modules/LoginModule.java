package us.idinfor.smartcitizen.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.us.lsi.smartcitizen.interactor.GetGoogleLogin;
import es.us.lsi.smartcitizen.interactor.UseCase;
import es.us.lsi.smartcitizen.repository.LoginRepository;
import rx.Scheduler;
import us.idinfor.smartcitizen.di.scopes.PerActivity;

@Module
public class LoginModule {

    @Provides
    @PerActivity
    @Named("google_login")
    public UseCase provideGetGoogleLoginUseCase(
            LoginRepository loginRepository,
            @Named("executor_thread") Scheduler executorThread,
            @Named("ui_thread") Scheduler uiThread){
        return new GetGoogleLogin(loginRepository, executorThread, uiThread);
    }

    /*@Provides
    @PerActivity
    @Named("signup") UseCase provideGetSignupUseCase(
            LoginRepository loginRepository,
            @Named("executor_thread") Scheduler executorThread,
            @Named("ui_thread") Scheduler uiThread){
        return new GetSignup(loginRepository, executorThread, uiThread);
    }*/
}
