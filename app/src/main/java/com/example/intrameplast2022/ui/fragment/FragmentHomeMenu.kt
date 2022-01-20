package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeBinding
import com.example.intrameplast2022.databinding.FragmentHomeMenuBinding

class FragmentHomeMenu : Fragment() {

    private lateinit var binding: FragmentHomeMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeMenuBinding.inflate(inflater, container, false)

        return binding.root
    }

}