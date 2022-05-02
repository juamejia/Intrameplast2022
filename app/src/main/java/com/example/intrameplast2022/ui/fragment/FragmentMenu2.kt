package com.example.intrameplast2022.ui.fragment

import com.example.intrameplast2022.dataSource.CourseModal
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intrameplast2022.MainActivity.Companion.courseModalArrayList
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentMenu2Binding
import com.example.intrameplast2022.ui.viewmodel.RecordAdapter

class FragmentMenu2 : Fragment() {

    private lateinit var binding: FragmentMenu2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenu2Binding.inflate(inflater, container, false)
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            rvFilesList.layoutManager = LinearLayoutManager(context)
            if (courseModalArrayList?.isEmpty()!!) {
                emptyList.isVisible = true
            } else {
                emptyList.isVisible = false
                rvFilesList.adapter = RecordAdapter(
                    courseModalArrayList!!
                ) { onItemSelected(it) } // Verification process
            }
        }
    }

    private fun onItemSelected(recordList: CourseModal) {
        val bundle = Bundle()
        bundle.putString("recordPhoto", recordList.getPhoto())
        bundle.putStringArrayList("recordSelected", recordList.getBasicInfo())
        bundle.putStringArrayList("tableSelected", recordList.getTableInfo())
        findNavController().navigate(R.id.action_fragmentMenu2_to_fragment_report_saved, bundle)
    }

}
