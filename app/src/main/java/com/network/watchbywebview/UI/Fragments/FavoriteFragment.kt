package com.network.watchbywebview.UI.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.network.watchbywebview.DATA.Database.RoomDB.Favorite.FavoriteEntity
import com.network.watchbywebview.R
import com.network.watchbywebview.UI.Activities.MainActivity
import com.network.watchbywebview.UI.Adapters.MainAdapter
import com.network.watchbywebview.ViewModel.FavoriteViewModel
import com.network.watchbywebview.ViewModel.WebViewViewModel

class FavoriteFragment : Fragment() {
    private lateinit var mainAdapter:MainAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val context: Context = requireContext()
        val FAVORITE = 1
        val favoriteVM: FavoriteViewModel by activityViewModels()
        val webViewVM : WebViewViewModel by activityViewModels()
        val isDarkTheme = (activity as MainActivity).getModeValue()

        val mainRecyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)

        favoriteVM.getFavorite()
        favoriteVM.getAll.observe(this, Observer { list ->

            mainAdapter = MainAdapter(context, list, FAVORITE, isDarkTheme,{ position ->
                webViewVM.setUrlSource(list[position].sourceUrl)
                (activity as MainActivity?)?.fragmentSwitcher(DisplayWebView(), 0)
            },{ position ->
                //delete
                val fav = FavoriteEntity(0, list[position].sourceName, list[position].iconUrl, list[position].sourceUrl)
                favoriteVM.deleteFavorite(fav)
                mainAdapter.delete(list[position])
            })

            mainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mainRecyclerView.adapter = mainAdapter

        })

//        val mainRecyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)
//        val mainAdapter = MainAdapter(context, favoriteResource())
//
//        mainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        mainRecyclerView.adapter = mainAdapter

        return view
    }
}