package com.network.watchbywebview.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.network.watchbywebview.R
import com.network.watchbywebview.UI.Activities.MainActivity
import com.network.watchbywebview.UI.Adapters.SearchResultAdapter
import com.network.watchbywebview.ViewModel.SearchResultVM
import com.network.watchbywebview.ViewModel.WebViewViewModel


class DisplaySearchResultFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display_search_result, container, false)
        val searchResultVM : SearchResultVM by activityViewModels()
        val webViewVM : WebViewViewModel by activityViewModels()
        val isDarkTheme = (activity as MainActivity).getModeValue()

        val context = requireContext()
        val recyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)

        searchResultVM.getAllResult.observe(this, Observer { list ->
            if(list != null){
                val adapter = SearchResultAdapter(context, list, isDarkTheme){
                        position ->
                    webViewVM.setUrlSource(list[position].sourceUrl)
                    (activity as MainActivity?)?.fragmentSwitcher(DisplayWebView(), 0)
                }
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
            }else{
                println("Search Result is Null or Empty")
            }
        })

        return view
    }
}