package com.hm.fomakin.passkeepres;

import android.app.Application;

import com.hm.fomakin.passkeepres.Di.Component.DaggerIDbHelperComponent;
import com.hm.fomakin.passkeepres.Di.Component.IDbHelperComponent;
import com.hm.fomakin.passkeepres.Di.Module.AppModule;

public class App extends Application {

    private static IDbHelperComponent dbHelperComponent;

    public static IDbHelperComponent getDbHelperComponent() {
        return dbHelperComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelperComponent = DaggerIDbHelperComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
