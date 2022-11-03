package com.network.watchbywebview.UI.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.network.watchbywebview.R
import com.network.watchbywebview.UI.Activities.MainActivity
import com.network.watchbywebview.UI.communication.ConnectionValidation


class NoInternetFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_no_internet, container, false)
        val context = requireContext()
        val btn = view.findViewById<Button>(R.id.reConnectionBtn)
        val text = view.findViewById<TextView>(R.id.textMessage)
        val MAIN_FRAGMENT = 0


        btn.setOnClickListener(View.OnClickListener {
            if (ConnectionValidation().isOnline(context)){
                (activity as MainActivity?)?.fragmentSwitcher(MainFragment(), MAIN_FRAGMENT)
            }else{
                text.text = getText(R.string.pleaseReCheck)
            }
        })

        return view
    }
}