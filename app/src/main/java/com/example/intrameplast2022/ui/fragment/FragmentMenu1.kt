package com.example.intrameplast2022.ui.fragment

import CourseModal
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.MainActivity.Companion.courseModalArrayList
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentMenu1Binding
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import kotlin.system.exitProcess


class FragmentMenu1 : Fragment() {

    private lateinit var binding: FragmentMenu1Binding

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

    var temp1 = false               // Aforo Q1
    var temp2 = false               // Aforo Q2

    var q1_0 = false
    var q1_1 = false
    var q1_2 = false
    var q1_3 = false
    var q1_4 = false
    var q1WPressure = 0.1           //  Presión de trabajo
    var q1Water = 0.0               //  Temp agua
    var q1FinalR = 0.0              //  Lectura final
    var q1InitialR = 0.0            //  Lectura inicial
    var q1ProofTime = 0.0           //  Tiempo

    var q2_0 = false
    var q2_1 = false
    var q2_2 = false
    var q2_3 = false
    var q2_4 = false
    var q2WPressure = 0.1
    var q2Water = 0.0
    var q2FinalR = 0.0
    var q2InitialR = 0.0
    var q2ProofTime = 0.0


    private val locale = Locale("id", "ID")
    private val df: DateFormat = SimpleDateFormat("dd/MM/yyyy", locale)


    override fun onResume() {
        super.onResume()
        // Dropdown settings
        val caliberAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, listOf("D15", "D20"))
        val metrologicalAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                listOf("B", "C", "R80", "R160", "R200")
            )
        val newOldAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, listOf("Nuevo", "Usado"))
        with(binding) {
            ddCaliber.setAdapter(caliberAdapter)
            ddMetrological.setAdapter(metrologicalAdapter)
            ddNewOld.setAdapter(newOldAdapter)
        }
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
        Logger.addLogAdapter(AndroidLogAdapter())
        showAlerts()

        with(binding) {
            // Auto date field
            tvDate2.setText(df.format(Date()))
            // Aforos setter
            ddCaliber.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, _, _ ->
                    temp1 = true
                    aforoCheck()
                }
            ddMetrological.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, _, _ ->
                    temp2 = true
                    aforoCheck()
                }
            // Q1 auto setter
            tiQ1WorkPressure0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q1WPressure = text.toString().toDoubleOrNull() ?: 0.0
                    q1_0 = true
                    measureQ1Check()
                    if (textQ1.visibility == View.VISIBLE) {
                        tvCaliber.error = null
                        tvMetrologicalClass.error = null

                    }else {
                        tvCaliber.error = " "
                        tvMetrologicalClass.error = getString(R.string.required2)
                    }
                }
            }
            tiQ1TWater0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q1Water = text.toString().toDoubleOrNull() ?: 0.0
                    q1_1 = true
                    measureQ1Check()
                }
            }
            tiQ1LF0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q1FinalR = text.toString().toDoubleOrNull() ?: 0.0
                    q1_2 = true
                    measureQ1Check()
                }
            }
            tiQ1LI0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q1InitialR = text.toString().toDoubleOrNull() ?: 0.0
                    q1_3 = true
                    measureQ1Check()
                }
            }
            tiQ1Time0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q1ProofTime = text.toString().toDoubleOrNull() ?: 0.0
                    q1_4 = true
                    measureQ1Check()
                }
            }
            // Q2 auto setter
            tiQ2WorkPressure0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q2WPressure = text.toString().toDoubleOrNull() ?: 0.0
                    q2_0 = true
                    measureQ2Check()
                    if (textQ2.visibility == View.VISIBLE) {
                        tvCaliber.error = null
                        tvMetrologicalClass.error = null

                    }else {
                        tvCaliber.error = " "
                        tvMetrologicalClass.error = getString(R.string.required2)
                    }
                }
            }
            tiQ2TWater0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q2Water = text.toString().toDoubleOrNull() ?: 0.0
                    q2_1 = true
                    measureQ2Check()
                }
            }
            tiQ2LF0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q2FinalR = text.toString().toDoubleOrNull() ?: 0.0
                    q2_2 = true
                    measureQ2Check()
                }
            }
            tiQ2LI0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q2InitialR = text.toString().toDoubleOrNull() ?: 0.0
                    q2_3 = true
                    measureQ2Check()
                }
            }
            tiQ2Time0.doOnTextChanged { text, start, before, count ->
                if (!text.isNullOrEmpty()) {
                    q2ProofTime = text.toString().toDoubleOrNull() ?: 0.0
                    q2_4 = true
                    measureQ2Check()
                }
            }

        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.progressBar.visibility = View.GONE
        binding.nestedScrollView.visibility = View.VISIBLE
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    private fun measureQ1Check() {
        with(binding) {
            if (q1_0 && q1_1 && q1_2 && q1_3 && q1_4 && temp1) {
                tvQ1Process.text = measureProcessQ1()
                tvResult.visibility = View.VISIBLE
            } else {
                tvQ1Process.text = getString(R.string.resultado_q2)
                tvResult.visibility = View.GONE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun measureQ2Check() {
        with(binding) {
            if (q2_0 && q2_1 && q2_2 && q2_3 && q2_4 && temp2) {
                binding.tvQ2Process.text = measureProcessQ2()
                tvResult.visibility = View.VISIBLE
            } else {
                binding.tvQ2Process.text = getString(R.string.resultado_q2)
                tvResult.visibility = View.GONE
            }
        }
    }

    private fun aforo(caudal: String): Double {
        var aforoQ1 = 0.0
        var aforoQ2 = 0.0
        var finalQ = 9.99

        with(binding) {

            // Measure with Q1 and Q2
            when (ddCaliber.editableText.toString()) {
                "D15" -> when (ddMetrological.editableText.toString()) {
                    "R80" -> {
                        aforoQ1 = 31.25
                        aforoQ2 = 50.0
                    }
                    "R160" -> {
                        aforoQ1 = 15.6
                        aforoQ2 = 25.0
                    }
                    "R200" -> {
                        aforoQ1 = 12.5
                        aforoQ2 = 20.0
                    }
                    "B" -> {
                        aforoQ1 = 30.0
                        aforoQ2 = 120.0
                    }
                    "C" -> {
                        aforoQ1 = 15.0
                        aforoQ2 = 22.5
                    }
                }
                "D20" -> when (ddMetrological.editableText.toString()) {
                    "R80" -> {
                        aforoQ1 = 50.0
                        aforoQ2 = 80.0
                    }
                    "R160" -> {
                        aforoQ1 = 25.0
                        aforoQ2 = 40.0
                    }
                    "R200" -> {
                        aforoQ1 = 20.0
                        aforoQ2 = 32.0
                    }
                    "B" -> {
                        aforoQ1 = 50.0
                        aforoQ2 = 200.0
                    }
                    "C" -> {
                        aforoQ1 = 25.0
                        aforoQ2 = 37.5
                    }
                }
            }

        }

        when (caudal) {
            "Q1" -> finalQ = aforoQ1
            "Q2" -> finalQ = aforoQ2
        }
        return finalQ
    }

    @SuppressLint("SetTextI18n")
    private fun aforoCheck() {
        if (temp1 && temp2) {
            with(binding) {
                textQ1.text = getString(R.string.aforo_text) + aforo("Q1")
                textQ2.text = getString(R.string.aforo_text) + aforo("Q2")
                textQ1.visibility = View.VISIBLE
                textQ2.visibility = View.VISIBLE
                measureQ1Check()
                measureQ2Check()
            }
        }
    }

    private fun showAlerts() {
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
            reloadFields()
        }
        binding.btSave.setOnClickListener { // Show logged info
            if (textFieldsCheck()) {
                toast("Registro guardado")
                courseModalArrayList!!.add(
                    with(binding) {
                        CourseModal(
                            "Imagine dragons photo",
                            arrayListOf(
                                tvDate2.editableText.toString(),
                                tvOperator2.editableText.toString().uppercase(),
                                tvUser2.editableText.toString().uppercase(),
                                tvLocation2.editableText.toString().uppercase(),
                                tvContract2.editableText.toString(),
                                tvMark2.editableText.toString().uppercase(),
                                tvSerial2.editableText.toString(),
                                ddCaliber.editableText.toString(),
                                ddMetrological.editableText.toString(),
                                ddNewOld.editableText.toString()
                            ),
                            arrayListOf(
                                tiQ1LI0.text.toString(),
                                tiQ2LI0.text.toString(),
                                tiQ1LF0.text.toString(),
                                tiQ2LF0.text.toString(),
                                tiQ1Time0.text.toString(),
                                tiQ2Time0.text.toString(),
                                tiQ1TWater0.text.toString(),
                                tiQ2TWater0.text.toString(),
                                tiQ1TEnvironment0.text.toString(),
                                tiQ2TEnvironment0.text.toString(),
                                tiQ1WorkPressure0.text.toString(),
                                tiQ2WorkPressure0.text.toString(),
                                tvQ1Process.text.toString(),
                                tvQ2Process.text.toString()
                            )
                        )
                    }
                )
                // notifying adapter when new data added.
                saveData()
            } else toast("¡Complete los campos requeridos!")
        }
    }

    private fun textFieldsCheck(): Boolean {
        var checked = true
        // Obligatory fields show an required text
        if (binding.tvMark2.text!!.isEmpty()) {
            binding.tvMark2.error = getString(R.string.required)
            checked = false
        } else binding.tvMark2.error = null
        if (binding.tvOperator2.text!!.isEmpty()) {
            binding.tvOperator2.error = getString(R.string.required)
            checked = false
        } else binding.tvOperator2.error = null
        if (binding.tvSerial2.text!!.isEmpty()) {
            binding.tvSerial2.error = getString(R.string.required)
            checked = false
        } else binding.tvSerial2.error = null
        return checked
    }

    private fun reloadFields() { // This function reload all table fields to generate a new measure
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
    }

    private fun measureProcessQ1(): String {
        // val aforo = 99.9
        val aforo = aforo("Q1")

        // Measure Q1

        var vCorregQ1 =
            (aforo + CVI_) * (1 + CETM_ * (q1Water - 20)) * (1 + CETV_ * (20 - q1Water) * (1 + CCA_ * (q1WPressure - PTR_))) // to code review

        val realFlowQ1 = (vCorregQ1 / q1ProofTime) * 3600       // Consult functionality
        val errorQ1 = (((q1FinalR - q1InitialR) - vCorregQ1) / vCorregQ1) * 100
        return ((errorQ1 * 100.0).roundToInt() / 100.0).toString() // To return double with 3 decimal digits only

    }

    private fun measureProcessQ2(): String {
        // val aforo = 9.99
        val aforo = aforo("Q2")

        // Measure Q2
        var vCorregQ2 =
            (aforo + CVI_) * (1 + CETM_ * (q2Water - 20)) * (1 + CETV_ * (20 - q2Water) * (1 + CCA_ * (q2WPressure - PTR_))) // to code review

        val realFlowQ2 = (vCorregQ2 / q2ProofTime) * 3600       // Consult functionality
        val errorQ2 = (((q2FinalR - q2InitialR) - vCorregQ2) / vCorregQ2) * 100
        return ((errorQ2 * 100.0).roundToInt() / 100.0).toString()

    }

    private fun toast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        val sharedPreferences = context?.getSharedPreferences(
            "shared preferences",
            AppCompatActivity.MODE_PRIVATE
        )

        // creating a variable for editor to
        // store data in shared preferences.
        val editor = sharedPreferences?.edit()

        // creating a new variable for gson.
        val gson = Gson()

        // getting data from gson and storing it in a string.
        val json = gson.toJson(courseModalArrayList)

        // below line is to save data in shared
        // prefs in the form of string.
        editor?.putString("courses", json)

        // below line is to apply changes
        // and save data in shared prefs.
        editor?.apply()

        // after saving data we are displaying a toast message.
    }

}