package com.example.grocerylist

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import androidx.lifecycle.Observer
import androidx.navigation.NavType
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [details.newInstance] factory method to
 * create an instance of this fragment.
 */
class List : Fragment() {
    val TAG = "Proj"

    //variables
    var newGroceryName : String = ""
    var newGroceryItem : GroceryItem = GroceryItem("")
    private lateinit var adapter:GroceryListAdapter
    private lateinit var model: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button
    //private lateinit var listState: Lifecycle.State


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        super.onCreateView(inflater, container, savedInstanceState)
        var view = inflater.inflate(R.layout.list_fragment, container, false)

        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapter = GroceryListAdapter(model.getGroceryList())

        recyclerView = view.findViewById(R.id.GroceryList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if (savedInstanceState != null){
            var savedRecyclerState : Parcelable? = savedInstanceState.getParcelable("list_state")
            recyclerView.layoutManager?.onRestoreInstanceState(savedRecyclerState)
        }

        //recyclerview.

//        adapter.setGroceries(model.getGroceryList())

//        model.getGroceryList().observe(viewLifecycleOwner, Observer<ArrayList<GroceryItem>> {groceries ->
//            groceries?.let {
//                adapter.setGroceries(it)
////                adapter.notifyDataSetChanged()
////                model.getGroceryList().postValue(it)
//            }
//
//        })


        val addGrocery:EditText = view.findViewById(R.id.GroceryText)

        addButton = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            Log.d(TAG, "opening dialog")
            openDialog()
        }


        val sortButton:Button = view.findViewById(R.id.AlphabetButton)

        addGrocery.setOnKeyListener(object:View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.KEYCODE_ENTER || event?.action == KeyEvent.ACTION_DOWN)
                {
                    val newGrocery:GroceryItem = GroceryItem(addGrocery.text.trim().toString())
                    model.addToList(newGrocery)
                    adapter.setGroceries(model.getGroceryList())
                    return true
                }
                addGrocery.text.clear()
                return false
            }
        })

        sortButton.setOnClickListener(){
            adapter.sortAlphabetically()
        }

        return view
    }

    fun openDialog(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
        builder.setTitle("New Item")

        //set up the input
        val input = EditText(activity)
        input.hint = "Item Name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        //set up buttons
        builder.setPositiveButton("OK") { dialog, which ->
            newGroceryName = input.text.toString()
            Log.d(TAG, "new grocery name: $newGroceryName")


            newGroceryItem = GroceryItem(newGroceryName)
            Log.d(TAG, "newGroceryItem: ${newGroceryItem}")
            model.addToList(newGroceryItem)
            adapter.setGroceries(model.getGroceryList())

        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putParcelable("list_state", recyclerView.layoutManager?.onSaveInstanceState())

        super.onSaveInstanceState(outState)

    }



    inner class GroceryListAdapter(private val dataSet:ArrayList<GroceryItem>) :
        RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder>() {


         private var groceries = emptyList<GroceryItem>()


        internal fun setGroceries(groceries: List<GroceryItem>) {

            this.groceries = groceries
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {

            return groceries.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {


            val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
            return GroceryViewHolder(v)


        }

        fun filter(text: String): ArrayList<GroceryItem> {
            var list: ArrayList<GroceryItem> = ArrayList()
            for (item in groceries) {
                if (item.foodName.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                    list.add(item)
                }
            }
            return list;
        }

        fun sortAlphabetically(): List<GroceryItem> {
            //return movies.sortedBy { it.title.toString() }
            return groceries.sortedWith(compareBy { it.foodName })
        }

        override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {

            holder.view.findViewById<TextView>(R.id.FoodName).text = dataSet[position].foodName

            holder.itemView.setOnClickListener() {
                // interact with the item
                holder.onClick(it)
            }

            holder.view.findViewById<Button>(R.id.remove).setOnClickListener {
                dataSet.removeAt(position)
                recyclerView.removeViewAt(position)
                model.removeFromList(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, dataSet.size)
                adapter.notifyDataSetChanged()
                Log.d(TAG, "list: remove from list")
            }

        }

        inner class GroceryViewHolder(val view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {
            override fun onClick(view: View?) {
                view?.findNavController()?.navigate(R.id.action_list_to_details)
            }




        }
    }
}