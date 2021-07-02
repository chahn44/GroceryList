package com.example.grocerylist

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

}