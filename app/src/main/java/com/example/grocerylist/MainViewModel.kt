package com.example.grocerylist

import android.content.ContentValues.TAG
import android.util.Log
import com.example.grocerylist.GroceryItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var groceryList = ArrayList<GroceryItem>()//change back to grocery item

    fun addToList(item: GroceryItem)
    {
        groceryList.add(item)
    }

    fun getGroceryList(): ArrayList<GroceryItem> {
        return groceryList
    }

    fun removeFromList(position: Int) {

        Log.d("Project", "model: remove from list; position = $position")
        //groceryList.removeAt(position-1)
    }

}