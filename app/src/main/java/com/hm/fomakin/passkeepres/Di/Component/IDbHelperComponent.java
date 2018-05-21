package com.hm.fomakin.passkeepres.Di.Component;

import com.hm.fomakin.passkeepres.CardModifyActivity;
import com.hm.fomakin.passkeepres.Di.Module.AppModule;
import com.hm.fomakin.passkeepres.Di.Module.DbMockHelperModule;
import com.hm.fomakin.passkeepres.Di.Module.DbSQLiteHelperModule;
import com.hm.fomakin.passkeepres.Di.Module.MainDbHelperModule;
import com.hm.fomakin.passkeepres.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

//@Component(modules = {AppModule.class, DbMockHelperModule.class})
//@Component(modules = {AppModule.class, DbSQLiteHelperModule.class})
@Component(modules = {AppModule.class, MainDbHelperModule.class})
@Singleton
public interface IDbHelperComponent {

    void inject(MainActivity mainActivity);
    void inject(CardModifyActivity cardModifyActivity);

}
