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

class MainAdapter (private val context: Context, private val list:MutableList<WDSource>, private val usage:Int, private val isDarkTheme:Boolean, val click: (position:Int) -> Unit, val iconClick: (position:Int) -> Unit) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.websiteIconImageView)
        val icon: ImageView = view.findViewById(R.id.heartIcon)
        val textView: TextView = view.findViewById(R.id.websiteNameTv)
        val container: RelativeLayout = view.findViewById(R.id.resourceContainer)
        val MAIN = 0
        val FAVORITE = 1
        val drawable: GradientDrawable = container.background.mutate() as GradientDrawable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_model, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(list[position].iconUrl).into(holder.imageView)
        holder.textView.text = list[position].sourceName

        holder.container.setOnClickListener(View.OnClickListener {
            click(position)
        })
        when(usage){
            holder.MAIN -> {
                holder.icon.setOnClickListener(View.OnClickListener {
                    holder.icon.setImageResource(R.drawable.fav_red_icon)
                    iconClick(position)
                })
            }
            holder.FAVORITE -> {
                holder.icon.setImageResource(R.drawable.delete_black_icon)
                holder.icon.setOnClickListener(View.OnClickListener {
                    iconClick(position)
                })
            }
        }

        if(isDarkTheme){
            holder.drawable.setStroke(3, Color.WHITE)
        }else{
            holder.drawable.setStroke(3, Color.BLACK)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun delete(wdSource: WDSource){
        list.remove(wdSource)
        this.notifyDataSetChanged()
    }
    fun displaySearchResult(resultList:ArrayList<WDSource>){
        list.clear()
        list.addAll(resultList)
    }
}