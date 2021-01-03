package com.example.dontspendtoomuch.ui.insight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dontspendtoomuch.R

class InsightFragment : Fragment() {

    private lateinit var insightViewModel: InsightViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        insightViewModel =
                ViewModelProvider(this).get(InsightViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_insight, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        insightViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}