package com.example.intrameplast2022.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.MainActivity.Companion.prefs
import com.example.intrameplast2022.R
import com.example.intrameplast2022.dataSource.Prefs
import com.example.intrameplast2022.databinding.FragmentMenu1Binding
import kotlin.math.roundToInt
import kotlin.system.exitProcess

class FragmentMenu1 : Fragment() {

    private lateinit var binding: FragmentMenu1Binding
    private val required: String = "Campo requerido"

    // Constants table
    private val CVI_ = 0.0

    //  Corrección del volumen indicado en el RVM, debido a la resolución de la escala
    private val CETM_ = 0.000051

    //  Coeficiente de expansión térmico volumétrico para el material del R.V.M.
    private val CETV_ = 0.00015

    //  Coeficiente de expansión térmico volumétrico del agua
    private val CCA_ = 0.00000046

    //  coeficiente de Compresibilidad del agua
    private val PTR_ = 0.0

    //  Presión en el tanque de recolección

    override fun onResume() {
        super.onResume()
        // Dropdown settings
        val caliberAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, listOf("D15", "D20"))
        val metrologicalAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, listOf("B", "C", "R160"))
        val newOldAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, listOf("Nuevo", "Usado"))
        binding.ddCaliber.setAdapter(caliberAdapter)
        binding.ddMetrological.setAdapter(metrologicalAdapter)
        binding.ddNewOld.setAdapter(newOldAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenu1Binding.inflate(inflater, container, false)
        // Back and Exit buttons, always the same in all fragments
        binding.btExit.setOnClickListener { exitProcess(0) }
        binding.btBack.setOnClickListener {
            findNavController().popBackStack() // Return to the preview fragment, in this case, always homeFragment
        }

        // Ask to Kevin :D
        // Measure observer q1
        binding.tiQ1WorkPressure0.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.tvQ1Process.text = measureProcessQ1()
                binding.tvResult.visibility = View.VISIBLE
            } else {
                binding.tvQ1Process.text = getString(R.string.resultado_q2)
                binding.tvResult.visibility = View.GONE
            }
        }

//      Copy to create Q2 measures
        binding.tiQ2WorkPressure0.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                binding.tvQ2Process.text = measureProcessQ2()
            } else {
                binding.tvQ2Process.text = getString(R.string.resultado_q2)
            }
        }
        showAlerts()
        return binding.root
    }

    private fun showAlerts() {
        binding.btQ1.setOnClickListener {
            toast("Caudal (1)")
        }
        binding.btQ2.setOnClickListener {
            toast("Caudal (2)")
        }
        binding.btPhoto.setOnClickListener {
            //toast("Fotografía")
            toast(prefs.getName())
        }
        binding.btReload.setOnClickListener {
            reloadFields()
            toast("Recargado")
        }
        binding.btSave.setOnClickListener { // Show logged info
            if (textFieldsCheck()) {
                toast("Registro guardado")
            } else toast("¡Complete los campos requeridos!")
        }
        binding.btPrint.setOnClickListener {
            textFieldsCheck()
            toast("Imprimir")
        }
    }

    private fun textFieldsCheck(): Boolean {
        var checked = true
        // Obligatory fields show an required text
        if (binding.tvMark2.text!!.isEmpty()) {
            binding.tvMark2.error = required
            checked = false
        } else binding.tvMark2.error = null
        if (binding.tvOperator2.text!!.isEmpty()) {
            binding.tvOperator2.error = required
            checked = false
        } else binding.tvOperator2.error = null
        if (binding.tvSerial2.text!!.isEmpty()) {
            binding.tvSerial2.error = required
            checked = false
        } else binding.tvSerial2.error = null
        return checked
    }

    private fun reloadFields() { // This function reload all table fields to generate a new measure
        // Measure basic info
        // -> Implementation required later
        // Table start
        binding.tiQ1LI0.setText("")
        binding.tiQ2LI0.setText("")
        binding.tiQ1LF0.setText("")
        binding.tiQ2LF0.setText("")
        binding.tiQ1Time0.setText("")
        binding.tiQ2Time0.setText("")
        binding.tiQ1TWater0.setText("")
        binding.tiQ2TWater0.setText("")
        binding.tiQ1TEnvironment0.setText("")
        binding.tiQ2TEnvironment0.setText("")
        binding.tiQ1WorkPressure0.setText("")
        binding.tiQ2WorkPressure0.setText("")
        // Table end
    }

    private fun measureProcessQ1(): String {
        val aforo = 99.9
        var vCorregQ1 = 0.0
        with(binding) {
            val q1WPressure = tiQ1WorkPressure0.text.toString().toDouble()   //  Presión de trabajo
            val q1Water = tiQ1TWater0.text.toString().toDouble()             //  Temp agua
            val q1FinalR = tiQ1LF0.text.toString().toDouble()                //  Lectura final
            val q1InitialR = tiQ1LI0.text.toString().toDouble()              //  Lectura inicial
            val q1ProofTime = tiQ1Time0.text.toString().toDouble()           //  Tiempo
            // Measure Q1
            if (q1WPressure > 0.0 && q1Water > 0.0) {
                vCorregQ1 =
                    (aforo + CVI_) * (1 + CETM_ * (q1Water - 20)) * (1 + CETV_ * (20 - q1Water) * (1 + CCA_ * (q1WPressure - PTR_))) // to code review
            }
            val realFlowQ1 = (vCorregQ1 / q1ProofTime) * 3600       // Consult functionality
            val errorQ1 = (((q1FinalR - q1InitialR) - vCorregQ1) / vCorregQ1) * 100
            return ((errorQ1 * 100.0).roundToInt() / 100.0).toString() // To return double with 3 decimal digits only
        }
    }

    private fun measureProcessQ2(): String {
        val aforo = 9.99
        var vCorregQ2 = 0.0
        with(binding) {
            val q2WPressure = tiQ2WorkPressure0.text.toString().toDouble()  //  Presión de trabajo
            val q2Water = tiQ2TWater0.text.toString().toDouble()            //  Temp agua
            val q2FinalR = tiQ2LF0.text.toString().toDouble()               //  Lectura final
            val q2InitialR = tiQ2LI0.text.toString().toDouble()             //  Lectura inicial
            val q2ProofTime = tiQ2Time0.text.toString().toDouble()          //  Tiempo
            // Measure Q2
            if (q2WPressure > 0.0 && q2Water > 0.0) {
                vCorregQ2 =
                    (aforo + CVI_) * (1 + CETM_ * (q2Water - 20)) * (1 + CETV_ * (20 - q2Water) * (1 + CCA_ * (q2WPressure - PTR_))) // to code review
            }
            val realFlowQ2 = (vCorregQ2 / q2ProofTime) * 3600       // Consult functionality
            val errorQ2 = (((q2FinalR - q2InitialR) - vCorregQ2) / vCorregQ2) * 100
            val errorQ2Final = ((errorQ2 * 100.0).roundToInt() / 100.0).toString() // To return double with 2 decimal digits only
            prefs.saveName(errorQ2Final)
            return errorQ2Final
        }
    }

    private fun toast(text: String) {
        // To use Toast inside fragment replace this by context
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}