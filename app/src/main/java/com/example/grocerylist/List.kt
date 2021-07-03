package com.example.grocerylist

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import androidx.lifecycle.Observer
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
        model.appendEvent("Add button clicked")
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
                    model.appendEvent("Item added to list")
                    adapter.setGroceries(model.getGroceryList())
                    model.appendEvent("List updated")
                    return true
                }
                addGrocery.text.clear()
                model.appendEvent("Keyboard down/Item entered")
                return false
            }
        })

        sortButton.setOnClickListener(){
//            adapter.sortAlphabetically(model.getGroceryList())
            model.appendEvent("Sort button clicked")
            adapter.setGroceries(adapter.sortAlphabetically())
            adapter.notifyDataSetChanged()
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
            model.appendEvent("Grocery Added to List")
            adapter.setGroceries(model.getGroceryList())
            model.appendEvent("List updated")

        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Details().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
    }






    }

    inner class GroceryListAdapter(private val dataSet:ArrayList<GroceryItem>) :
        RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder>() {


         private var groceries = emptyList<GroceryItem>()


        internal fun setGroceries(groceries: List<GroceryItem>) {

            this.groceries = groceries
            notifyDataSetChanged()
            model.appendEvent("List updated")
        }

        override fun getItemCount(): Int {

            return groceries.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {


            val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
            return GroceryViewHolder(v)


        }


        fun sortAlphabetically(): List<GroceryItem> {
            model.appendEvent("List sorted by alphabet")
            return groceries.sortedWith(compareBy { it.getFood() })
//            return groceries.sortedBy { it.foodName }
//            return groceries.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER, {it.foodName}))
        }

        override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {

            holder.view.findViewById<TextView>(R.id.FoodName).text = dataSet[position].foodName

            holder.itemView.setOnClickListener() {
                // interact with the item
                model.appendEvent("Item clicked")
                holder.onClick(it)
            }

            holder.view.findViewById<Button>(R.id.remove).setOnClickListener {
                dataSet.removeAt(position)
                recyclerView.removeViewAt(position)
                model.removeFromList(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, dataSet.size)
                adapter.notifyDataSetChanged()
                model.appendEvent("Grocery Removed from list")
                Log.d(TAG, "list: remove from list")
            }

        }

        inner class GroceryViewHolder(val view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {
            override fun onClick(view: View?) {
                model.appendEvent("Recylcerview Item clicked (List clicked)")
                view?.findNavController()?.navigate(R.id.action_list_to_details,
                    bundleOf("food" to groceries.get(this.layoutPosition).foodName))
            }


        }
    }
}