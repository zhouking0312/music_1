package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.FragmentAdapter
import com.example.myapplication.Bean.SingletonMusicInfo


class Fragment_1 : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = this.dialog?.window
        window?.decorView?.setPadding(0,0,0,0)
        val lp = window?.attributes
        lp?.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp?.height = WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = 600
        lp?.gravity = Gravity.BOTTOM
        lp?.y = 300

        window?.attributes = lp
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        val recyclerview  = view.findViewById<RecyclerView>(R.id.rec_fragmnet)
        recyclerview.layoutManager = LinearLayoutManager(context)
        var data = SingletonMusicInfo.getAllMusic()
        val adpter = FragmentAdapter(data)
        recyclerview.adapter = adpter

        return view
    }
}