package com.regmoraes.popularmovies.data.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.regmoraes.popularmovies.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public class NetworkModule {

    private final String CONNECTIVITY_INTERCEPTOR = "CONNECTIVITY";
    private final String AUTHENTICATION_INTERCEPTOR = "AUTHENTICATION";

    @Provides
    @Singleton
    public Gson providesGson() {

        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .excludeFieldsWithoutExposeAnnotation()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor providesLoggingInterceptor() {

        HttpLoggingInterceptor loggingInterceptor =
                new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return loggingInterceptor;
    }

    @Provides
    @Named(CONNECTIVITY_INTERCEPTOR)
    @Singleton
    public Interceptor providesInternetConnectivityInterceptor(Context context) {

        return chain -> {

            if (!NetworkUtils.hasInternetConnection(context)) {
                throw new NoInternetException();
            }

            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        };
    }

    @Provides
    @Named(AUTHENTICATION_INTERCEPTOR)
    @Singleton
    public Interceptor providesAuthenticationInterceptor() {

        return chain -> {

            Request originalRequest = chain.request();
            HttpUrl originalUrl = originalRequest.url();

            HttpUrl newUrl = originalUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIES_DB_API_KEY)
                    .build();

            Request newRequest = originalRequest.newBuilder()
                    .url(newUrl).build();

            return chain.proceed(newRequest);
        };
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttp(@Named(AUTHENTICATION_INTERCEPTOR) Interceptor authenticationInterceptor,
                                       @Named(CONNECTIVITY_INTERCEPTOR) Interceptor connectivityInterceptor,
                                       HttpLoggingInterceptor loggingInterceptor) {

        return new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofit(OkHttpClient okHttpClient, Gson gson) {

        String MOVIES_DB_BASE_URL = "http://api.themoviedb.org/3/";

        return new Retrofit.Builder()
                .baseUrl(MOVIES_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }
}
