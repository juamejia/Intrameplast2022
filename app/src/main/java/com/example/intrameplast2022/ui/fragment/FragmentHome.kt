package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide() // to hide action bar
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentHomeMenu)
        }

        return binding.root
    }
}