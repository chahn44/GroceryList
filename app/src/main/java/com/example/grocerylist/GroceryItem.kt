package com.example.grocerylist

import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "grocery_table")
    data class GroceryItem(@PrimaryKey @ColumnInfo(name ="calories") var calories: Int,
                         @ColumnInfo(name ="food_image") var food_image: ImageView,
                         @ColumnInfo(name ="food_amount") var food_amount: Int,
                         @ColumnInfo(name ="price") var price: Double,
                         @ColumnInfo (name = "food_name") var food_name:String
//                         @ColumnInfo(name ="poster_path") var poster_path: String,)
    )