package mudin.iv.recipeapp.di;

import mudin.iv.recipeapp.utils.NavigationManager;
import mudin.iv.recipeapp.viewmodel.MainViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


//the injection class is define at here
@Module
public class UtilsModule {

    @Provides
    @Singleton
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    MainViewModel getViewModelFactory() {
        return new MainViewModel();
    }

    @Provides
    @Singleton
    NavigationManager getNavigationManager() {
        return new NavigationManager();
    }
}
