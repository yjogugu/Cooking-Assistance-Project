package com.taijoo.cookingassistance.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.taijoo.cookingassistance.R
import com.taijoo.cookingassistance.data.model.SearchCategoryData

class CustomCategoryAutoCompleteAdapter(private val context: Context, private val mItem: List<SearchCategoryData>) : BaseAdapter() {


    override fun getCount(): Int {
        return mItem.size
    }

    override fun getItem(p0: Int): Any {
        return mItem[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(i: Int, p1: View?, viewGroup: ViewGroup?): View {
        val rootView: View = LayoutInflater.from(context)
            .inflate(R.layout.list_item_arrayadapter, viewGroup, false)

        val textView = rootView.findViewById<TextView>(R.id.spinnerText)

        textView.text = mItem[i].category
        return rootView
    }


}