package mudin.iv.recipeapp.view

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import mudin.iv.recipeapp.MyApplication
import mudin.iv.recipeapp.R
import mudin.iv.recipeapp.model.Recipe
import mudin.iv.recipeapp.model.RecipeType
import mudin.iv.recipeapp.utils.XMLPullParserHandler
import mudin.iv.recipeapp.viewmodel.MainViewModel
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.*
import javax.inject.Inject


class FormActivity : AppCompatActivity() {
    var context: FormActivity? = null

    @Inject
    lateinit var viewModel: MainViewModel

    var btnConfirm : LinearLayout? = null
    lateinit var etTitle : TextView
    lateinit var spinnerType : Spinner
    lateinit var etSteps : TextView
    lateinit var etIngredients : TextView
    lateinit var etImageLink : TextView
    lateinit var mainTitle : TextView
    lateinit var tvConfirm : TextView
    lateinit var btnBack : ImageView
    var id : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        context = this
        btnConfirm = findViewById(R.id.saveButton)

        (application as MyApplication).appComponent.doInjection(this)
        viewModel.refreshRecipeList()

        //initView
        mainTitle  =  findViewById(R.id.toolbarTitle)
        btnBack  =  findViewById(R.id.btnBack)
        etTitle  =  findViewById(R.id.etTitle)
        spinnerType  =  findViewById(R.id.spinnerType)
        etSteps  =  findViewById(R.id.etSteps)
        etIngredients  =  findViewById(R.id.etIngredients)
        etImageLink  =  findViewById(R.id.etImageLink)
        tvConfirm = findViewById(R.id.tvConfirm)
        initView()

        viewModel.userMutableLiveData.observe(this@FormActivity, {
            // Update the UI when the data has changed
        })

        //check if any id is passed? passed means update, none means creating new one
        val key = getId()
        if(key != null){
            id  = key.toIntOrNull()
            if(id  != null){
                val recipe = viewModel.getRecipeByPosition(id!!)
                if(recipe != null){
                    fillInTheData(recipe)
                }
            }
        }

    }

    fun getId() : String? {
        var data: String? = null
        if (intent != null && intent.extras != null) {
            val bundle = intent.extras
            if (bundle?.getString("id") != null) {
                data = bundle.getString("id")
            }
        }

        return data
    }

    //fill in the view with data
    fun fillInTheData(recipe: Recipe){
        etTitle.text = recipe.title
        spinnerType.setSelection(0)
        etIngredients.text = recipe.ingredient
        etSteps.text = recipe.steps
        etImageLink.text = recipe.imageLink

        tvConfirm.text = "Save Recipe"
    }


    fun initView(){
        mainTitle.text = "Add new Recipe List"

        setupSpinnerFilter()

        //clicklistener init at here

        btnConfirm?.setOnClickListener {
            //button will proceed only if the variable needed is fulfilled
            //checker if any infomation is null

            if(etTitle.text == ""){
                etTitle.error = "Please insert title to the recipe"
                return@setOnClickListener
            }

            if(etSteps.text == ""){
                etSteps.error = "Please insert steps to the recipe"
                return@setOnClickListener
            }

            if(etIngredients.text == ""){
                etIngredients.error = "Please insert ingredients to the recipe"
                return@setOnClickListener
            }

            var recipe = Recipe()

            recipe.title = etTitle.text.toString()
            recipe.type = spinnerType.selectedItem.toString()
            recipe.steps =etSteps.text.toString()
            recipe.ingredient = etIngredients.text.toString()

            if(id != null){
                doSave(id!!, recipe)
            } else {
                createNew(recipe)
            }

        }

        btnBack.visibility = View.VISIBLE
        btnBack.setOnClickListener {
            finish()
        }

    }

    fun doSave(position: Int, recipe: Recipe){ //save old obejct
        viewModel.updateRecipe(position, recipe)
        finish()
    }

    fun createNew(recipe: Recipe){ //create new object
        viewModel.addNewRecipe(recipe)
        finish()
    }

    private fun setupSpinnerFilter() {
        try {
            val parser = XMLPullParserHandler()
            val recipeTypes: List<RecipeType> = parser.parse(assets.open("recipetypes.xml"))
            val spinnerArrayAdapter: ArrayAdapter<RecipeType> =
                ArrayAdapter<RecipeType>(this, android.R.layout.simple_spinner_item, recipeTypes)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = spinnerArrayAdapter
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}



