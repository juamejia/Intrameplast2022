package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeMenuBinding
import com.example.intrameplast2022.databinding.FragmentMenu1Binding
import com.example.intrameplast2022.databinding.FragmentMenu1NewBinding
import kotlin.system.exitProcess

class fragmentMenu1New : Fragment() {
    private lateinit var binding: FragmentMenu1NewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenu1NewBinding.inflate(inflater, container, false)

        with(binding){
            btExit.setOnClickListener { exitProcess(0) }
            btBack.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentMenu1New_to_fragmentHomeMenu)
            }
        }

        return binding.root
    }

}