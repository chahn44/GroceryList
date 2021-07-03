package com.example.grocerylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Final_Project"
        const val USERNAME = "Group_Project"
        const val URL = "https://posthere.io/"
        const val ROUTE = "c3f3-4e2b-8d20"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

    }
}