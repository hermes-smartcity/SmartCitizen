package us.idinfor.smartcitizen.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Scheduler;
import us.idinfor.smartcitizen.view.activity.BaseActivity;
import us.idinfor.smartcitizen.data.api.hermes.HermesCitizenApi;
import us.idinfor.smartcitizen.di.modules.ApplicationModule;
import us.idinfor.smartcitizen.di.modules.NetworkModule;
import us.idinfor.smartcitizen.di.scopes.PerApp;

@PerApp
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    Context context();
    SharedPreferences sharedPreferences();
    @Named("executor_thread") Scheduler executorThread();
    @Named("computation_thread") Scheduler computationThread();
    @Named("io_thread") Scheduler ioThread();
    @Named("ui_thread") Scheduler uiThread();

    OkHttpClient okHttpClient();
    Gson gson();
    Retrofit.Builder retrofitBuilder();
    HermesCitizenApi hermesCitizenApi();

    UserRepository userRepository();

}