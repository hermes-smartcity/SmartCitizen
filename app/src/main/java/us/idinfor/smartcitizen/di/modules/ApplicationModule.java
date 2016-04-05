package us.idinfor.smartcitizen.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.us.lsi.smartcitizen.repository.LoginDataRepository;
import es.us.lsi.smartcitizen.repository.LoginRepository;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import us.idinfor.smartcitizen.BuildConfig;
import us.idinfor.smartcitizen.Constants;
import us.idinfor.smartcitizen.SmartCitizenApplication;
import us.idinfor.smartcitizen.data.api.google.fit.GoogleFitHelper;

@Module
public class ApplicationModule {

    private final SmartCitizenApplication mApplication;

    public ApplicationModule(SmartCitizenApplication application) {
        this.mApplication = application;

        if(BuildConfig.DEBUG){
            //LeakCanary.install(this.mApplication);
        }else{
            //Fabric.with(this.mApplication, new Crashlytics());
        }

        if(!TextUtils.isEmpty(provideDefaultSharedPreferences().getString(Constants.PROPERTY_USER_NAME, ""))){
            GoogleFitHelper.initFitApi(provideApplicationContext());
        }
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideDefaultSharedPreferences() {
        return PreferenceManager
                .getDefaultSharedPreferences(this.mApplication);
    }

    @Provides
    @Singleton
    LoginRepository provideLoginRepository(LoginDataRepository loginDataRepository){
        return loginDataRepository;
    }

    @Provides
    @Singleton
    @Named("executor_thread")
    Scheduler provideExecutorThread(){
        return Schedulers.newThread();
    }

    @Provides
    @Singleton
    @Named("computation_thread")
    Scheduler provideComputationThread(){
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @Named("io_thread")
    Scheduler provideIOThread(){
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named("ui_thread")
    Scheduler provideUIThread(){
        return AndroidSchedulers.mainThread();
    }

}
