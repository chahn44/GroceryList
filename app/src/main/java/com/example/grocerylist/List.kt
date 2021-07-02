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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List


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
    var newGroceryItem : GroceryItem = GroceryItem(food_name = "")
    private var adapter = GroceryListAdapter()
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

        recyclerView = view.findViewById(R.id.GroceryList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


//        val addGrocery:EditText = view.findViewById(R.id.GroceryText)

        addButton = view.findViewById(R.id.addButton)
        addButton.setOnClickListener (object:View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d(TAG, "opening dialog")
                openDialog()

            }
        })


        val sortButton:Button = view.findViewById(R.id.AlphabetButton)

//        val newGrocery:String = addGrocery.text.trim().toString()

//        addGrocery.setOnKeyListener(object:View.OnKeyListener{
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
//                if (event?.action == KeyEvent.KEYCODE_ENTER || event?.action == KeyEvent.ACTION_DOWN)
//                {
//                    model.addToList(newGrocery)
//                    return true
//                }
//                return false
//            }
//        })

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


            newGroceryItem = GroceryItem(food_name = newGroceryName)
            Log.d(TAG, "newGroceryItem: ${newGroceryItem.food_name}")
            model.addToList(newGroceryItem)

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

    inner class GroceryListAdapter() :
        RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder>() {


        private var groceries = emptyList<GroceryItem>()


        internal fun setMovies(groceries: List<GroceryItem>) {

            this.groceries = groceries
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {

            return groceries.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {


            val v = LayoutInflater.from(parent.context).inflate(R.layout.list_fragment, parent, false)
            return GroceryViewHolder(v)
        }

        fun filter(text: String): ArrayList<GroceryItem> {
            var list: ArrayList<GroceryItem> = ArrayList()
            for (item in groceries) {
                if (item.food_name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                    list.add(item)
                }
            }
            return list;
        }

        fun sortAlphabetically(): List<GroceryItem> {
            //return movies.sortedBy { it.title.toString() }
            return groceries.sortedWith(compareBy { it.food_name })
        }

        override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {


            //holder.bindItems(movieList[position])

//            Glide.with(this@ListFragment)
//                .load(resources.getString(R.string.picture_base_url) + movies[position].poster_path)
//                .apply(RequestOptions().override(128, 128))
//                .into(holder.view.findViewById(R.id.poster))

//            holder.view.findViewById<TextView>(R.id.title).text = movies[position].title
//
//            holder.view.findViewById<TextView>(R.id.rating).text =
//                movies[position].vote_average.toString()


            holder.itemView.setOnClickListener() {
                // interact with the item
                holder.onClick(it)

            }

        }

        inner class GroceryViewHolder(val view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {
            override fun onClick(view: View?) {

            }


        }
    }
}