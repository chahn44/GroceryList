package com.example.grocerylist

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class Dialog : DialogFragment(), DialogInterface.OnClickListener{
    //cant figure out, going to do in list.kt

//    private lateinit var itemName : EditText
//    private lateinit var itemNum : EditText
    override fun onClick(dialog: DialogInterface?, which: Int) {

    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//
//            val builder = AlertDialog.Builder(it)
//            val inflater = requireActivity().layoutInflater
//
//            builder.setView(inflater.inflate(R.layout.layout_dialog,null))
//                .setPositiveButton(R.string.)
//
//
//        }
//    } ?: throw IllegalStateException("Activity cannot be null")

//    private lateinit var view : View
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        var builder = AlertDialog.Builder(activity)
//
//        var inflater: LayoutInflater = requireActivity().layoutInflater
//        view = inflater.inflate(R.layout.layout_dialog,null)
//
//        builder.setView(view)
//            .setTitle("New Item")
//            .setNegativeButton("Cancel", this)
//            .setPositiveButton("OK", this)
//
//        return builder.create()
//    }
//
//    override fun onClick(dialog: DialogInterface?, which: Int) {
//        val clicked = when (which) {
//            Dialog.BUTTON_NEGATIVE -> dialog?.dismiss()
//            Dialog.BUTTON_POSITIVE -> {
//                itemName = view.findViewById(R.id.food_name)
//                itemNum = view.findViewById(R.id.food_number)
//            }
//            else -> "Text Here"
//        }
//    }
}