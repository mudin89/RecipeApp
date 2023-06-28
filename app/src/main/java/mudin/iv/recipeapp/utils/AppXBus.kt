package mudin.iv.recipeapp.utils

import mudin.iv.recipeapp.model.Recipe
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

public object AppXBus {

    //the central noticer for the app.
    //act ad delivery man between activity/fragment etc

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)

    class AppEvents{

        class UpdateLatestRecipeList(var recipeList: ArrayList<Recipe>){
            fun getList():ArrayList<Recipe>{
                return recipeList
            }
        }

        class refreshRecipeList()
    }
}