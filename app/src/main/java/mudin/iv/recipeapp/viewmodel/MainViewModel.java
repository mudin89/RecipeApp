package mudin.iv.recipeapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
//        populateList();
        ArrayList<Recipe> storeList = PaperDbManager.RECIPE.INSTANCE.readRecipeList();

        if(storeList == null){
            storeList = new ArrayList<>();
        }

        recipeArrayList = storeList;
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
        Recipe recipe = new Recipe();
        recipe.setTitle("Automated Testing Script");
        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Automated Testing Script 2");
        Recipe recipe3 = new Recipe();
        recipe3.setTitle("Automated Testing Script 3");
        Recipe recipe4 = new Recipe();
        recipe4.setTitle("Automated Testing Script 4");
        Recipe recipe5 = new Recipe();
        recipe5.setTitle("Automated Testing Script 5");


        recipeArrayList.add(recipe);
        recipeArrayList.add(recipe2);
        recipeArrayList.add(recipe3);
        recipeArrayList.add(recipe4);
        recipeArrayList.add(recipe5);
        recipeArrayList.add(recipe);
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
