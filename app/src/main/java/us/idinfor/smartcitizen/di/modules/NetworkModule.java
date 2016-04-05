package us.idinfor.smartcitizen.di.modules;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import us.idinfor.smartcitizen.data.api.hermes.HermesCitizenApi;

@Module
public class NetworkModule {

    private final static long SECONDS_TIMEOUT = 20;

    @Provides
    @Singleton
    Cache provideOkHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .connectTimeout(SECONDS_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(SECONDS_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(SECONDS_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient);
    }

    @Provides
    @Singleton
    @Named("retrofit_hermescitizen")
    Retrofit provideHermesCitizenRetrofit(Retrofit.Builder builder) {
        return builder
                .baseUrl(HermesCitizenApi.SERVICE_ENDPOINT)
                .build();
    }

    @Provides
    @Singleton
    HermesCitizenApi provideHermesCitizenApi(
            @Named("retrofit_hermescitizen") Retrofit retrofit) {
        return retrofit.create(HermesCitizenApi.class);
    }

}
