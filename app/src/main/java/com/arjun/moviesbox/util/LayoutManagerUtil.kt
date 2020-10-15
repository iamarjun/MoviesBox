package com.arjun.moviesbox.util

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

object LayoutManagerUtil {

    fun getHorizontalLayoutManager(context: Context): LinearLayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.HORIZONTAL,
        false
    )

    fun getVerticalLayoutManager(context: Context): LinearLayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )

    fun getGridLayoutManager(context: Context, span: Int): GridLayoutManager = GridLayoutManager(
        context,
        span
    )

}