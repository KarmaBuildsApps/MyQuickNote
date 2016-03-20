package myapp.tae.ac.uk.myquicknote.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import myapp.tae.ac.uk.myquicknote.R;

/**
 * Created by Karma on 15/03/16.
 */
@Module
public class LocalDataModule {

    @Singleton
    @Provides
    Realm provideRealmInstance(Context context) {
        return Realm.getInstance(context);
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.preference_name),
                context.MODE_PRIVATE);
    }
}
