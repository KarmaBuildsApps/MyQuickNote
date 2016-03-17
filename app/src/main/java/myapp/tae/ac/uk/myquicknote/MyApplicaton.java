package myapp.tae.ac.uk.myquicknote;

import android.app.Application;

//import myapp.tae.ac.uk.myquicknote.di.components.DaggerLocalDataComponent;
import myapp.tae.ac.uk.myquicknote.di.components.LocalDataComponent;
import myapp.tae.ac.uk.myquicknote.di.modules.AppModule;
import myapp.tae.ac.uk.myquicknote.di.modules.LocalDataModule;

/**
 * Created by Karma on 15/03/16.
 */
public class MyApplicaton extends Application {
//    private LocalDataComponent mDataComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        mDataComponent = DaggerLocalDataComponent.builder()
//                .appModule(new AppModule(this))
//                .localDataModule(new LocalDataModule())
//                .build();
    }

//    public LocalDataComponent getLocalDataComponent() {
//        return mDataComponent;
//    }
}
