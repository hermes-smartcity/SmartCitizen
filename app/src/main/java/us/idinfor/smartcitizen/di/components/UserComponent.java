package us.idinfor.smartcitizen.di.components;

import dagger.Component;
import us.idinfor.smartcitizen.di.modules.ActivityModule;
import us.idinfor.smartcitizen.di.modules.UserModule;
import us.idinfor.smartcitizen.di.scopes.PerActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, UserModule.class} )
public interface UserComponent extends ActivityComponent {

    UseCase getUserDetailsUseCase();
}
