package com.etatech.downloaderapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*

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

        p0.appNameTV.text = items.get(p1).name
        p0.artistTV.text = items.get(p1).artist
        p0.realeaseDateTv.text = items.get(p1).releaseDate
        p0.summaryTV.text = items.get(p1).summary

        Glide.with(context)
            .load(items.get(p1).image)
            .placeholder(R.drawable.ic_android)
            .error(R.drawable.ic_android_red)
            .into(p0.appIcon)


        val item: FeedEntery = items[p1]
        p0.bind(items[p1])

//        p0.parent_layout.setOnClickListener {
//            event.invoke(getAdapterPosition(), getItemViewType())
//            p0.subItems.visibility = View.VISIBLE

        }

}

class CustomViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val parent_layout = view.parent_layout
    val subItems = view.subItems

    val appNameTV = view.appName_tv
    val artistTV = view.artistTV
    val summaryTV = view.summaryTV
    val realeaseDateTv = view.releaseDateTV
    val appIcon = view.appIcon

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        parent_layout.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    fun bind(feedEntery: FeedEntery) {
        parent_layout.setOnClickListener(View.OnClickListener {
            if(subItems.visibility==View.GONE) {
                subItems.visibility = View.VISIBLE
            }else
                subItems.visibility = View.GONE
        })
    }

}