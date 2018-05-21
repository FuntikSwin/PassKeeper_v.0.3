package com.hm.fomakin.passkeepres.Di.Module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.fomakin.passkeepres.Database.IDbHelper;
import com.hm.fomakin.passkeepres.Database.MainDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainDbHelperModule {

    @Provides
    @NonNull
    @Singleton
    public IDbHelper provideMainDbHelper(@NonNull Context context) {
        return new MainDbHelper(context);
    }

}
