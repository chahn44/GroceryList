package com.example.grocerylist

import com.example.grocerylist.GroceryItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var groceryList = MutableLiveData<ArrayList<GroceryItem>>()//change back to grovery item

    fun addToList(item: GroceryItem)
    {
        groceryList.value?.add(item)
    }

}