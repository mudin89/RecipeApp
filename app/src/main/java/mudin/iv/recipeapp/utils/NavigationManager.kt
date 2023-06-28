package mudin.iv.recipeapp.utils

import android.content.Context
import android.content.Intent
import mudin.iv.recipeapp.view.FormActivity

class NavigationManager(){

    //centralize navigator... can monitor the data passed from multiple activity by using this and handle it

    fun toFormActivity(context: Context, id: String?){
        val intent = Intent(context, FormActivity::class.java)
        if(id != null){
            intent.putExtra("id", id)
        }

        context.startActivity(intent)
    }


}