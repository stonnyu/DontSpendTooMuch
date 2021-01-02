package com.example.dontspendtoomuch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dontspendtoomuch.databinding.FragmentAddSpendingBinding
import com.example.dontspendtoomuch.databinding.FragmentCategoryBinding
import java.util.*

class AddSpendingFragment : Fragment() {

    private lateinit var binding: FragmentAddSpendingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddSpendingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}