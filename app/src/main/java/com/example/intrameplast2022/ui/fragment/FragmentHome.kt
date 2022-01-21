package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btIngress.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentHomeMenu)
        }

        return binding.root
    }
}