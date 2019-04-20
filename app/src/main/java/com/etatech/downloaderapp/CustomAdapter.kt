package com.etatech.downloaderapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_layout.view.*

/****************************************************
 **Created by Fady Fouad on 2019-04-20 at 01:27 AM.**
 ***************************************************/
class CustomAdapter (val items : ArrayList<FeedEntery>, val context: Context) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CustomViewHolder {

        return CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, p0, false))

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: CustomViewHolder, p1: Int) {

        p0?.itemTV?.text = items.get(p1).toString()

    }

}

class CustomViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val itemTV = view.item_tv
}