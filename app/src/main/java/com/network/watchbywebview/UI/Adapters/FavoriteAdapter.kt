package com.network.watchbywebview.UI.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.DATA.DataClasses.WebSiteSource
import com.network.watchbywebview.R
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val context: Context, private val list:MutableList<WDSource>, val clicked: (position:Int) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById<ImageView>(R.id.favoriteImageView)
        val textView: TextView = view.findViewById<TextView>(R.id.favoriteTextView)
        val favoriteContainer: CardView = view.findViewById<CardView>(R.id.favoriteContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.favorite_model, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(list[position].iconUrl).into(holder.imageView)
        holder.textView.text = list[position].sourceName
        holder.favoriteContainer.setOnClickListener(View.OnClickListener {
            clicked(position)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun add(wdSource: WDSource) {
        list.add(wdSource)
        notifyDataSetChanged()

    }
    fun displaySearchResult(resultList:ArrayList<WDSource>){
    }
}