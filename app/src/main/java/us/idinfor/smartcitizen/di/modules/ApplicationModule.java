package us.idinfor.smartcitizen.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.fabric.sdk.android.Fabric;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import us.idinfor.smartcitizen.BuildConfig;
import us.idinfor.smartcitizen.Constants;
import us.idinfor.smartcitizen.SmartCitizenApplication;
import us.idinfor.smartcitizen.data.api.google.fit.GoogleFitHelper;
import us.idinfor.smartcitizen.di.scopes.PerApp;

@Module
public class ApplicationModule {

    private final SmartCitizenApplication mApplication;

    public ApplicationModule(SmartCitizenApplication application) {
        this.mApplication = application;

        if(BuildConfig.DEBUG){
            LeakCanary.install(this.mApplication);
        }else{
            Fabric.with(this.mApplication, new Crashlytics());
        }

        if(!TextUtils.isEmpty(provideDefaultSharedPreferences().getString(Constants.PROPERTY_USER_NAME, ""))){
            GoogleFitHelper.initFitApi(provideApplicationContext());
        }
    }

    @Provides
    @PerApp
    Context provideApplicationContext() {
        return this.mApplication;
    }

    @Provides
    @PerApp
    SharedPreferences provideDefaultSharedPreferences() {
        return PreferenceManager
                .getDefaultSharedPreferences(this.mApplication);
    }

    @Provides
    @PerApp
    UserRepository provideUserRepository(UserDataRepository userDataRepository){
        return userDataRepository;
    }

    @Provides
    @PerApp
    @Named("executor_thread")
    Scheduler provideExecutorThread(){
        return Schedulers.newThread();
    }

    @Provides
    @PerApp
    @Named("computation_thread")
    Scheduler provideComputationThread(){
        return Schedulers.computation();
    }

    @Provides
    @PerApp
    @Named("io_thread")
    Scheduler provideIOThread(){
        return Schedulers.io();
    }

    @Provides
    @PerApp
    @Named("ui_thread")
    Scheduler provideUIThread(){
        return AndroidSchedulers.mainThread();
    }

}
