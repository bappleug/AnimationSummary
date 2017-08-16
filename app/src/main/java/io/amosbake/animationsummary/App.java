package io.amosbake.animationsummary;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Ray on 2017/8/16.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
