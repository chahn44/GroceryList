package com.example.grocerylist

import com.example.grocerylist.GroceryItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var groceryList = MutableLiveData<ArrayList<String>>()//change back to grovery item

    fun addToList(item: String)
    {
        groceryList.value?.add(item)
    }

}