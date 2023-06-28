package mudin.iv.recipeapp.di;


import mudin.iv.recipeapp.view.FormActivity;
import mudin.iv.recipeapp.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


//interface to do the injection to any activity/fragment

@Component(modules = {mudin.iv.recipeapp.di.UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MainActivity mainActivity);
    void doInjection(FormActivity formActivity);

}
