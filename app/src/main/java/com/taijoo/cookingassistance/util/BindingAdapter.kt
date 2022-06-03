package com.taijoo.cookingassistance.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.taijoo.cookingassistance.R

object BindingAdapter {

    @BindingAdapter("bindImage")
    @JvmStatic
    fun bindViewImage(imageView: ImageView, url : String?) {

        val requestOptions: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false)
            .centerCrop()
            .priority(Priority.LOW)

        Glide.with(imageView.context)
            .load(IP.SERVER_IP+url)
            .fallback(R.drawable.basics_profile)
            .apply(requestOptions)
            .into(imageView)


    }

    @BindingAdapter("toolbarOnBackPressed")
    @JvmStatic
    fun bindToolbar(toolbar: Toolbar, activity : Activity) {

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

    }

}