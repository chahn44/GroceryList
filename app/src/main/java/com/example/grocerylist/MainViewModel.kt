package com.example.grocerylist

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source


class MainViewModel : ViewModel() {

    //fetch reference database
    var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    val listItems = db?.collection("listItems")?.document("groceries")

    val source = Source.CACHE

    private var groceryList = ArrayList<GroceryItem>()//change back to grocery item

    fun addToList(item: GroceryItem)
    {
        //update groceryList
        groceryList.add(item)

        //re-send groceryList to database
        //listItems.set()

    }

    fun getGroceryList(): ArrayList<GroceryItem> {
        //set groceryList to list in database -> how to turn document into list?
        //groceryList = listItems.get()

        return groceryList
    }

    fun removeFromList(name: GroceryItem) {

        //update groceryList (done in list by listener)

        //re-send groceryList to database

        //Log.d("Project", "model: remove from list; position = $position")
        //groceryList.removeAt(position-1)
//        db?.collection("listItems")?.document("$name")
//            .delete()
//            .addOnSuccessListener { Log.d("project", "DocumentSnapshot successfully deleted!") }
//            .addOnFailureListener { e -> Log.w("project", "Error deleting document", e) }

    }
}