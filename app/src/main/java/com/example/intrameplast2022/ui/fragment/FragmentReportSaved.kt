package com.example.intrameplast2022.ui.fragment

import CourseModal
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.MainActivity.Companion.printer
import com.example.intrameplast2022.Printer
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentReportSavedBinding
import kotlin.system.exitProcess

class FragmentReportSaved : Fragment() {

    private lateinit var binding: FragmentReportSavedBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportSavedBinding.inflate(inflater, container, false)
        // Buttons
        binding.btExit.setOnClickListener { exitProcess(0) }
        binding.btBack.setOnClickListener {
            findNavController().popBackStack()  // Return to the preview fragment
        }

        // Setters
        with(binding) {
            // Basic info
            tvFecha.text = getString(R.string.fecha_r) + " ${getBundleBasicInfo()!![0]}"
            tvOperador.text = getString(R.string.operador_r) + " ${getBundleBasicInfo()!![1]}"
            tvUsuario.text = getString(R.string.usuario_r) + " ${getBundleBasicInfo()!![2]}"
            tvDireccion.text = getString(R.string.direcci_n_r) + " ${getBundleBasicInfo()!![3]}"
            tvMark.text = getString(R.string.marca_r) + " ${getBundleBasicInfo()!![4]}"
            tvSerial.text = getString(R.string.serial_r) + " ${getBundleBasicInfo()!![5]}"
            tvCaliber.text = getString(R.string.calibre_r) + " ${getBundleBasicInfo()!![6]}"
            tvMetrologicalClass.text =
                getString(R.string.clase_metrol_gica_r) + " ${getBundleBasicInfo()!![7]}"
            tvNewOld.text = getString(R.string.estado_r) + " ${getBundleBasicInfo()!![8]}"
            // Table
            tiQ1LI.text = getBundleTable()!![0]
            tiQ2LI.text = getBundleTable()!![1]
            tiQ1LF.text = getBundleTable()!![2]
            tiQ2LF.text = getBundleTable()!![3]
            tiQ1Time.text = getBundleTable()!![4]
            tiQ2Time.text = getBundleTable()!![5]
            tiQ1TWater.text = getBundleTable()!![6]
            tiQ2TWater.text = getBundleTable()!![7]
            tiQ1TEnvironment.text = getBundleTable()!![8]
            tiQ2TEnvironment.text = getBundleTable()!![9]
            tiQ1WorkPressure.text = getBundleTable()!![10]
            tiQ2WorkPressure.text = getBundleTable()!![11]
            tvQ1Process.text = getBundleTable()!![12]
            tvQ2Process.text = getBundleTable()!![13]
        }

        binding.btPrint.setOnClickListener {
            printer.doPrint()
        }

        return binding.root
    }

    private fun getBundleBasicInfo(): ArrayList<String>? {
        // Record selected by user
        return arguments?.get("recordSelected") as ArrayList<String>?
    }

    private fun getBundleTable(): ArrayList<String>? {
        return arguments?.get("tableSelected") as ArrayList<String>?
    }

}