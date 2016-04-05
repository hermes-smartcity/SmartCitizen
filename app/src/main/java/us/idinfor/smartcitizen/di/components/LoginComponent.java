package us.idinfor.smartcitizen.di.components;

import javax.inject.Named;

import dagger.Component;
import es.us.lsi.smartcitizen.interactor.UseCase;
import us.idinfor.smartcitizen.di.modules.ActivityModule;
import us.idinfor.smartcitizen.di.modules.LoginModule;
import us.idinfor.smartcitizen.di.scopes.PerActivity;
import us.idinfor.smartcitizen.view.fragment.LoginFragment;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, LoginModule.class} )
public interface LoginComponent extends ActivityComponent {

    void inject (LoginFragment loginFragment);

    @Named("google_login")
    UseCase getGoogleLoginUseCase();
    //UseCase getSignupUseCase();
}
