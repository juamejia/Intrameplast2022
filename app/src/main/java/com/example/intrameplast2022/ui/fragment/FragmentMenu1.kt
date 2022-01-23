package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeBinding
import com.example.intrameplast2022.databinding.FragmentMenu1Binding
import kotlin.system.exitProcess

class FragmentMenu1 : Fragment() {

    private lateinit var binding: FragmentMenu1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenu1Binding.inflate(inflater, container, false)
        // Back and Exit buttons, always the same in all fragments
        binding.btExit.setOnClickListener { exitProcess(0) }
        binding.btBack.setOnClickListener{
            findNavController().popBackStack() // Return to the preview fragment, in this case, always homeFragment
        }

        binding.btQ1.setOnClickListener {
            toast("Caudal (1)")
        }
        binding.btQ2.setOnClickListener {
            toast("Caudal (2)")
        }
        binding.btPhoto.setOnClickListener {
            toast("Fotografía")
        }
        binding.btReload.setOnClickListener {
            toast("Recargado")
        }
        binding.btSave.setOnClickListener {
            toast("Registro guardado")
        }
        binding.btPrint.setOnClickListener {
            toast("Imprimir")
        }

        // Implementing an exposed dropdown menu

        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.tvCaliber.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        // End implementation

        return binding.root
    }

    private fun toast(text: String) {
        // To use Toast inside fragment replace this by context
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}