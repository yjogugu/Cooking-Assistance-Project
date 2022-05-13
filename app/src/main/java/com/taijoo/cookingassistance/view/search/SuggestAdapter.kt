package com.taijoo.cookingassistance.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.taijoo.cookingassistance.data.model.StorageMaterialData
import java.util.*
import kotlin.collections.ArrayList


class SuggestAdapter (mcontext: Context, private val viewResourceId : Int, var mItems: List<StorageMaterialData>) : ArrayAdapter<StorageMaterialData>(
    mcontext, viewResourceId, mItems
){

    var tempItems : ArrayList<StorageMaterialData> = ArrayList<StorageMaterialData>(mItems)
    var suggestions : ArrayList<StorageMaterialData> = ArrayList<StorageMaterialData>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if(view == null){
            val vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = vi.inflate(viewResourceId,null)
        }

        val text = view!!.findViewById<TextView>(android.R.id.text1)


        val item = mItems[position]
        text.text = item.name

        return view
    }
//
//    override fun getFilter(): Filter {
//        return nameFilter!!
//    }
//
//    var nameFilter: Filter? = object : Filter() {
//        override fun convertResultToString(resultValue: Any): CharSequence {
//            return (resultValue as StorageMaterialData).name
//        }
//
//        override fun performFiltering(constraint: CharSequence): FilterResults {
//            return if (constraint != null) {
//                suggestions.clear()
//                for (people in tempItems) {
//                    if (people.name.toLowerCase()
//                            .contains(constraint.toString().lowercase(Locale.getDefault()))
//                    ) {
//                        suggestions.add(people)
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = suggestions
//                filterResults.count = suggestions.size
//                filterResults
//            } else {
//                FilterResults()
//            }
//        }
//
//        override fun publishResults(constraint: CharSequence, results: FilterResults) {
//            val filterList: List<StorageMaterialData> = results.values as ArrayList<StorageMaterialData>
//            if (results != null && results.count > 0) {
//                clear()
//                for (people in filterList) {
//                    add(people)
//                    notifyDataSetChanged()
//                }
//            }
//        }
//    }

}
