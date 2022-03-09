package com.example.intrameplast2022.ui.fragment

import CourseModal
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intrameplast2022.MainActivity.Companion.courseModalArrayList
import com.example.intrameplast2022.databinding.FragmentMenu2Binding
import com.example.intrameplast2022.ui.viewmodel.RecordAdapter
import kotlin.system.exitProcess

class FragmentMenu2 : Fragment() {

    private lateinit var binding: FragmentMenu2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenu2Binding.inflate(inflater, container, false)
        // Back and Exit buttons, always the same in all fragments
        binding.btExit.setOnClickListener { exitProcess(0) }
        binding.btBack.setOnClickListener {
            findNavController().popBackStack() // Return to the preview fragment, in this case, always homeFragment
        }

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            rvFilesList.layoutManager = LinearLayoutManager(context)
            if (courseModalArrayList?.isEmpty()!!){
                Toast.makeText(context, "Empty list", Toast.LENGTH_SHORT).show()
                emptyList.isVisible = true
            }else{
                emptyList.isVisible = false
                rvFilesList.adapter = RecordAdapter(
                    courseModalArrayList!!
                ) { onItemSelected(it) } // Verification process
            }
        }
    }

    private fun onItemSelected(recordList: CourseModal) {
        Toast.makeText(context, recordList.courseName, Toast.LENGTH_SHORT).show()
    }

}