package mudin.iv.recipeapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mudin.iv.recipeapp.utils.AppConstants;
import mudin.iv.recipeapp.utils.PaperDbManager;
import mudin.iv.recipeapp.model.Recipe;
import mudin.iv.recipeapp.utils.AppXBus;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    //view model for the app
    MutableLiveData<ArrayList<Recipe>> recipeLiveData;
    ArrayList<Recipe> recipeArrayList;

    @Inject
    public MainViewModel() {
        recipeLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Recipe>> getUserMutableLiveData() {
        return recipeLiveData;
    }

    public void init(){

        ArrayList<Recipe> storeList = PaperDbManager.RECIPE.INSTANCE.readRecipeList();

        if(storeList == null){
            storeList = new ArrayList<>();
        }

        recipeArrayList = storeList;


        if(recipeArrayList.size() <= 0){
            populateList();
        }
        recipeLiveData.setValue(recipeArrayList);
    }

    public Recipe getRecipeByPosition(int index){
        if(recipeArrayList != null && !(index >= recipeArrayList.size())){
            return recipeArrayList.get(index);
        }

        return null;
    }

    public void populateList(){
        recipeArrayList = new ArrayList<>();
        AppConstants.PREFILLED pf = AppConstants.PREFILLED.INSTANCE;

        Recipe recipe = new Recipe(pf.getTITLE_ONE(), pf.getTYPE_ONE(), pf.getINGREDIENT_ONE(), pf.getSTEPS_ONE(),pf.getIMAGE_ONE());
        Recipe recipe2 = new Recipe(pf.getTITLE_TWO(), pf.getTYPE_TWO(), pf.getINGREDIENT_TWO(), pf.getSTEPS_TWO(),pf.getIMAGE_TWO());
        Recipe recipe3 = new Recipe(pf.getTITLE_THREE(), pf.getTYPE_THREE(), pf.getINGREDIENT_THREE(), pf.getSTEPS_THREE(),pf.getIMAGE_THREE());
        Recipe recipe4 = new Recipe(pf.getTITLE_ONE(), pf.getTYPE_ONE(), pf.getINGREDIENT_ONE(), pf.getSTEPS_ONE(),pf.getIMAGE_ONE());
        Recipe recipe5 = new Recipe(pf.getTITLE_TWO(), pf.getTYPE_TWO(), pf.getINGREDIENT_TWO(), pf.getSTEPS_TWO(),pf.getIMAGE_TWO());


        recipeArrayList.add(recipe);
        recipeArrayList.add(recipe2);
        recipeArrayList.add(recipe3);
        recipeArrayList.add(recipe4);
        recipeArrayList.add(recipe5);
        recipeArrayList.add(recipe);

        PaperDbManager.RECIPE.INSTANCE.saveRecipeList(recipeArrayList);
    }

    public void addNewRecipe(Recipe recipe){
        recipeArrayList.add(recipe);
        recipeLiveData.postValue(recipeArrayList);
        PaperDbManager.RECIPE.INSTANCE.saveRecipeList(recipeArrayList);
        AppXBus.INSTANCE.publish(new AppXBus.AppEvents.refreshRecipeList());
    }

    public void updateRecipe(int position, Recipe recipe){
        recipeArrayList.set(position,recipe);
        recipeLiveData.postValue(recipeArrayList);
        PaperDbManager.RECIPE.INSTANCE.saveRecipeList(recipeArrayList);
        AppXBus.INSTANCE.publish(new AppXBus.AppEvents.refreshRecipeList());
    }


    public void refreshRecipeList(){
        recipeArrayList =  PaperDbManager.RECIPE.INSTANCE.readRecipeList();
        recipeLiveData.postValue(recipeArrayList);
    }

    public void updateCheckBoxRecipe(int position){
//        recipeArrayList.set(position);
    }
}
