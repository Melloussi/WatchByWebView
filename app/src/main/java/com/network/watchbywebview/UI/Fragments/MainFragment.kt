package com.network.watchbywebview.UI.Fragments

import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.activityViewModels

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.network.watchbywebview.DATA.DataClasses.WDSource
import com.network.watchbywebview.DATA.DataClasses.WebSiteSource
import com.network.watchbywebview.R
import com.network.watchbywebview.UI.Activities.MainActivity
import com.network.watchbywebview.UI.Adapters.FavoriteAdapter
import com.network.watchbywebview.UI.Adapters.MainAdapter
import com.network.watchbywebview.UI.communication.Communicat
import com.network.watchbywebview.UI.communication.ConnectionValidation
import com.network.watchbywebview.ViewModel.FavoriteViewModel
import com.network.watchbywebview.ViewModel.WDSourceVM
import com.network.watchbywebview.ViewModel.WebViewViewModel
import okhttp3.internal.notifyAll

class MainFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteList: MutableList<WDSource>
    private val WEBVIEW_FRAGMENT = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //requireContext().theme.applyStyle(R.style.darkTheme, true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val context: Context = requireContext()
        val MAIN = 0
        val mainVM: WDSourceVM by activityViewModels()
        val favoriteVM: FavoriteViewModel by activityViewModels()
        val webViewVM: WebViewViewModel by activityViewModels()
        val isDarkTheme = (activity as MainActivity).getModeValue()
        val progress = view.findViewById<ProgressBar>(R.id.progress)
        val fragmentCallback = activity as Communicat


        if (ConnectionValidation().isOnline(context)) {

            //Favorite
            val favoriteRecyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
            favoriteVM.getFavorite()
            favoriteVM.getAll.observe(this, Observer { list ->
                progress.visibility = View.GONE
                favoriteAdapter = FavoriteAdapter(context, list) { position ->
                    webViewVM.setUrlSource(list[position].sourceUrl)
//                (activity as MainActivity?)?.fragmentSwitcher(DisplayWebView(), WEBVIEW_FRAGMENT)

                    fragmentCallback.openWebView(list[position].sourceUrl)
                }
                favoriteList = list

                println("-------> Favorite part : $list")

                favoriteRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                favoriteRecyclerView.adapter = favoriteAdapter

                favoriteAdapter.registerAdapterDataObserver(object :
                    RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        println("--------> Something Change")
                        super.onChanged()
                        println("--------> Something Change")
                    }

                })
            })

            //Main
            val mainRecyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)
            mainVM.getSource()
            mainVM.getAll.observe(this, Observer { list ->
                val mainAdapter = MainAdapter(context, list, MAIN, isDarkTheme, { position ->
                    webViewVM.setUrlSource(list[position].sourceUrl)
//                (activity as MainActivity?)?.fragmentSwitcher(DisplayWebView(), WEBVIEW_FRAGMENT)
                    fragmentCallback.openWebView(list[position].sourceUrl)
                }, { position ->
                    favoriteVM.insertFavorite(list[position])
                    favoriteAdapter.add(list[position])
                })

                mainRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                mainRecyclerView.adapter = mainAdapter
            })

        } else {
            (activity as MainActivity?)?.fragmentSwitcher(NoInternetFragment(), 4)
        }



        return view
    }
}