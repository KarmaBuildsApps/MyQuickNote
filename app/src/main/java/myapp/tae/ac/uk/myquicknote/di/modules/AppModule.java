package myapp.tae.ac.uk.myquicknote.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Karma on 15/03/16.
 */
@Module
public class AppModule {
    private Context mApplication;

    public AppModule(Context application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    Context providesApplicationContext() {
        return mApplication;
    }
}