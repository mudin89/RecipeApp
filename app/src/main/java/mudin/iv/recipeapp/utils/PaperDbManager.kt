package mudin.iv.recipeapp.utils

import mudin.iv.recipeapp.model.Recipe
import io.paperdb.Paper

class PaperDbManager {

    //paperdb manager as simple database... can use ROOM in the future

    object RECIPE {
        private const val RECIPE_LIST = "RECIPE_LIST"
        private const val RECIPE_OBJECT = "RECIPE_OBJECT"
        private const val ISFIRSTRUN = "ISFIRSTRUN"

        fun saveIsFirstRun(firstRun : Boolean){
            Paper.book().write(ISFIRSTRUN, firstRun)
        }

        fun isFirstRun() : Boolean{
            return Paper.book().read(ISFIRSTRUN, true)
        }

        fun saveRecipe(list : Recipe){
            Paper.book().write(RECIPE_LIST, list)
        }

        fun readRecipe() : Recipe{
            return Paper.book().read(RECIPE_LIST, Recipe(AppConstants.PREFILLED.TITLE_ONE,AppConstants.PREFILLED.TYPE_ONE,AppConstants.PREFILLED.INGREDIENT_ONE,AppConstants.PREFILLED.STEPS_ONE,AppConstants.PREFILLED.IMAGE_ONE))
        }

        //save list
        fun saveRecipeList(list : ArrayList<Recipe>){
            Paper.book().write(RECIPE_OBJECT, list)

        }

        //read list
        fun readRecipeList() : ArrayList<Recipe>?{
            return Paper.book().read(RECIPE_OBJECT, arrayListOf())
        }

        //remove here
        fun removeAllList() {
            Paper.book().delete(RECIPE_LIST)
            Paper.book().delete(RECIPE_OBJECT)
        }
    }

}