package com.taijoo.cookingassistance.util

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


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