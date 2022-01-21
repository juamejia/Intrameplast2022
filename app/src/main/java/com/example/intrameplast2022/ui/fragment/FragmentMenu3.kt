package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.databinding.FragmentHomeBinding
import com.example.intrameplast2022.databinding.FragmentMenu3Binding
import kotlin.system.exitProcess

class FragmentMenu3 : Fragment() {

    private lateinit var binding: FragmentMenu3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenu3Binding.inflate(inflater, container, false)
        // Back and Exit buttons, always the same in all fragments
        binding.btExit.setOnClickListener { exitProcess(0) }
        binding.btBack.setOnClickListener{
            findNavController().popBackStack() // Return to the preview fragment, in this case, always homeFragment
        }

        return binding.root
    }

}