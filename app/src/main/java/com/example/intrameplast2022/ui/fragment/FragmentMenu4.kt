package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intrameplast2022.databinding.FragmentMenu4Binding

class FragmentMenu4 : Fragment() {

    private lateinit var binding: FragmentMenu4Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenu4Binding.inflate(inflater, container, false)

        return binding.root
    }

}