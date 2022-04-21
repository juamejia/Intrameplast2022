package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeBinding
import com.example.intrameplast2022.databinding.FragmentHomeMenuBinding
import kotlin.system.exitProcess

class FragmentHomeMenu : Fragment() {

    private lateinit var binding: FragmentHomeMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeMenuBinding.inflate(inflater, container, false)
        binding.btExit.setOnClickListener { exitProcess(0) }

        binding.btBack.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentHome) // Return to the preview fragment, in this case, always homeFragment
        }

        binding.bt1Proof.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentMenu1)
        }

        binding.bt2Review.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentMenu2)
        }

        binding.bt3Export.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentMenu3)
        }

        return binding.root
    }

}