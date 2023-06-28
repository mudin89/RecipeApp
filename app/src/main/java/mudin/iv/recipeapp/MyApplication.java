package mudin.iv.recipeapp;

import android.app.Application;
import android.content.Context;

import mudin.iv.recipeapp.di.AppComponent;
import mudin.iv.recipeapp.di.DaggerAppComponent;
import mudin.iv.recipeapp.di.UtilsModule;

//using ViewModel for reactive ui update
// This is the app aplication, the dagger component is defined at here

public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
