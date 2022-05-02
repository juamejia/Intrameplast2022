package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeMenuBinding

class FragmentHomeMenu : Fragment() {
    private lateinit var binding: FragmentHomeMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding = FragmentHomeMenuBinding.inflate(inflater, container, false)

        with(binding) {
            bt1Proof.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentMenu1)
            }
            bt2Review.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentMenu2)
            }
            bt3Export.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentHomeMenu_to_fragmentMenu3)
            }
        }

        return binding.root
    }

}