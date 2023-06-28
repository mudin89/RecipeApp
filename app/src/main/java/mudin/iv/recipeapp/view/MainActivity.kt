package mudin.iv.recipeapp.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mudin.iv.recipeapp.utils.NavigationManager
import mudin.iv.recipeapp.MyApplication
import mudin.iv.recipeapp.R
import mudin.iv.recipeapp.model.Recipe
import mudin.iv.recipeapp.utils.AppXBus
import mudin.iv.recipeapp.view.adapter.RecyclerViewAdapter
import mudin.iv.recipeapp.viewmodel.MainViewModel
import io.paperdb.Paper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var context: MainActivity? = null
    var recyclerView: RecyclerView? = null
    var recyclerViewAdapter: RecyclerViewAdapter? = null
    var btnAdd: ImageView? = null

    //using injecton for singleton instance --> save memory preformance
    @Inject
    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this

        //init of paperdb manager
        Paper.init(this@MainActivity)

        //init of dagger injection
        (application as MyApplication).getAppComponent().doInjection(this)

        //initializing the views
        val mainTitle  =  findViewById<TextView>(R.id.toolbarTitle)
        recyclerView = findViewById(R.id.rcvMain)
        btnAdd = findViewById(R.id.btnAdd)

        mainTitle.text = "Recipe List"

        //init viewmodel funtionality at here
        viewModel = ViewModelProviders.of(context!!).get(MainViewModel::class.java)
        viewModel.getUserMutableLiveData().observe(context!!, recipeListUpdateObserver)



        //click and appxbus listener was init here with observable to prevent any crash
        btnAdd?.setOnClickListener{
            navigationManager.toFormActivity(this@MainActivity, null)
        }

        //added listener for any update on live data
        val cbListener = updateCheckBoxListener()
        compositeDisposable.add(cbListener)
        val refreshListener = refreshListListener()
        compositeDisposable.add(refreshListener)
    }


    //init viewmodel observer
    var recipeListUpdateObserver: Observer<ArrayList<Recipe>> = Observer<ArrayList<Recipe>> { recipeArrayList ->
        recyclerViewAdapter = RecyclerViewAdapter(context, recipeArrayList)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = recyclerViewAdapter
    }

    private fun updateCheckBoxListener(): Disposable {
        val d = AppXBus.listen(AppXBus.AppEvents.UpdateLatestRecipeList::class.java)
                .subscribe({ position ->
                    updateCheckBox(position)
                }, { t: Throwable? -> })
        return d
    }

    //refresh/update the list from any where (ie another activity/ adapter)
    private fun refreshListListener(): Disposable {
        val d = AppXBus.listen(AppXBus.AppEvents.refreshRecipeList::class.java)
                .subscribe({ ok ->
                    refreshList()
                }, { t: Throwable? -> })
        return d
    }

    fun updateCheckBox(utlcb: AppXBus.AppEvents.UpdateLatestRecipeList){
        //
    }

    fun refreshList() {
        viewModel.refreshRecipeList()
    }

    override fun onDestroy() { //clear any observable here to prevent crashed
        super.onDestroy()
        compositeDisposable.clear()
    }
}