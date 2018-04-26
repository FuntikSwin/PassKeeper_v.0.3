package com.hm.fomakin.passkeepres.Di.Module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.fomakin.passkeepres.Database.DbMockHelper;
import com.hm.fomakin.passkeepres.Database.IDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbMockHelperModule {

    @Provides
    @NonNull
    @Singleton
    public IDbHelper provideDbMockHelper(@NonNull Context context) {
        return new DbMockHelper();
    }

}
