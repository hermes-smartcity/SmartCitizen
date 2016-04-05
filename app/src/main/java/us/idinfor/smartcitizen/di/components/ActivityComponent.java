package us.idinfor.smartcitizen.di.components;

import android.app.Activity;

import dagger.Component;
import us.idinfor.smartcitizen.di.modules.ActivityModule;
import us.idinfor.smartcitizen.di.scopes.PerActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity activity();

    /*void inject (BaseActivity baseActivity);
    void inject (BaseFragment baseFragment);
    void inject (MainActivity mainActivity);*/

}