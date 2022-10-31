package com.network.watchbywebview.UI.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.R
import com.squareup.picasso.Picasso

class SearchResultAdapter (private val context: Context, private val list:MutableList<WDSource>, private val isDarkTheme:Boolean, val click: (position:Int) -> Unit) : RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.websiteIconImageView)
        val textView: TextView = view.findViewById(R.id.websiteNameTv)
        val container: RelativeLayout = view.findViewById(R.id.search_container)
        val drawable: GradientDrawable = container.background.mutate() as GradientDrawable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_result_model, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(list[position].iconUrl).into(holder.imageView)
        holder.textView.text = list[position].sourceName

        holder.container.setOnClickListener(View.OnClickListener {
            click(position)
        })
        if(isDarkTheme){
            holder.drawable.setStroke(3, Color.WHITE)
        }else{
            holder.drawable.setStroke(3, Color.BLACK)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

}