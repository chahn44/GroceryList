
package com.example.grocerylist

import android.content.ContentValues.TAG
import android.util.Log
import com.example.grocerylist.GroceryItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    private var groceryList = ArrayList<GroceryItem>()
    private var selectedItem = GroceryItem(foodName = "")

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


    fun appendEvent(event: String){

        var date:String = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
        var time:String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        WorkManager.getInstance().beginUniqueWork(MainActivity.TAG, ExistingWorkPolicy.KEEP, OneTimeWorkRequestBuilder<UploadWorker>().setInputData(
            workDataOf("username" to MainActivity.USERNAME, "event" to event, "date" to "$date $time")
        )
            .build()).enqueue()
    }

    fun setSelectedItem(groceryItem: GroceryItem) {
        selectedItem = groceryItem

    }

    fun getSelectedItem(): GroceryItem {
        return selectedItem
    }

}