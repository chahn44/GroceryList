package com.example.grocerylist.ui.main

import GroceryItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var groceryList = MutableLiveData<ArrayList<GroceryItem>>()

    fun addToList(item: String)
    {
        groceryList.value?.add(item)
    }

}