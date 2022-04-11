package com.example.intrameplast2022.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentReportSavedBinding
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class FragmentReportSaved : Fragment() {

    private lateinit var binding: FragmentReportSavedBinding

    // Thermal printer

    private val locale = Locale("id", "ID")
    private val df: DateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", locale)
    private val nf = NumberFormat.getCurrencyInstance(locale)

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
            // Set photo
            ivPhoto.setImageBitmap(getBundlePhoto())
            // Basic info
            tvFecha.text = getString(R.string.fecha_r) + " ${getBundleBasicInfo()!![0]}"
            tvOperador.text = getString(R.string.operador_r) + " ${getBundleBasicInfo()!![1]}"
            tvUsuario.text = getString(R.string.usuario_r) + " ${getBundleBasicInfo()!![2]}"
            tvDireccion.text = getString(R.string.direcci_n_r) + " ${getBundleBasicInfo()!![3]}"
            tvNContrato.text = getString(R.string.n_contrato_r) + " ${getBundleBasicInfo()!![4]}"
            tvMark.text = getString(R.string.marca_r) + " ${getBundleBasicInfo()!![5]}"
            tvSerial.text = getString(R.string.serial_r) + " ${getBundleBasicInfo()!![6]}"
            tvCaliber.text = getString(R.string.calibre_r) + " ${getBundleBasicInfo()!![7]}"
            tvMetrologicalClass.text =
                getString(R.string.clase_metrol_gica_r) + " ${getBundleBasicInfo()!![8]}"
            tvNewOld.text = getString(R.string.estado_r) + " ${getBundleBasicInfo()!![9]}"
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
            doPrint()
        }

        return binding.root
    }

    private fun getBundlePhoto(): Bitmap {
        val imageBytes = Base64.decode(arguments?.get("recordPhoto") as String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    private fun getBundleBasicInfo(): ArrayList<String>? {
        // Record selected by user
        return arguments?.get("recordSelected") as ArrayList<String>?
    }

    private fun getBundleTable(): ArrayList<String>? {
        return arguments?.get("tableSelected") as ArrayList<String>?
    }

    private fun doPrint() {
        try {
            val connection = BluetoothPrintersConnections.selectFirstPaired()
            if (connection != null) {
                val printer = EscPosPrinter(connection, 203, 48f, 32)
                val text =
                        "[C]<b>Prueba de medidor</b>\n" +
                        "[C]--------------------------------\n" +
                        "[L]Fecha: ${getBundleBasicInfo()!![0]}\n" +
                        "[L]Operador: ${getBundleBasicInfo()!![1]}\n" +
                        "[L]Usuario: ${getBundleBasicInfo()!![2]}\n" +
                        "[L]Dirección: ${getBundleBasicInfo()!![3]}\n" +
                        "[L]Número de contrato: ${getBundleBasicInfo()!![4]}\n" +
                        "[C]================================\n" +
                        "[C]<b>Especificaciones</b>\n" +
                        "[L]Marca: ${getBundleBasicInfo()!![5]}\n" +
                        "[L]Serial: ${getBundleBasicInfo()!![6]}\n" +
                        "[L]Calibre: ${getBundleBasicInfo()!![7]}\n" +
                        "[L]Clase metrológica: ${getBundleBasicInfo()!![8]}\n" +
                        "[L]Estado: ${getBundleBasicInfo()!![9]}\n\n" +


                        "[L]    <b>MEDICIONES[R]Q2[R]Q1</b>" + "\n" +
                                "<b>----------------------------------</b>\n" +
                        "[L]Lectura" + "\n" + "inicial <b>(L)</b>" + "[R]${getBundleTable()!![1]}[R]${getBundleTable()!![0]}\n" +
                                "----------------------------------\n" +
                        "[L]Lectura" + "\n" + "final <b>(L)</b>" + "[R]${getBundleTable()!![3]}[R]${getBundleTable()!![2]}\n" +
                                "----------------------------------\n" +
                        "[L]Tiempo <b>(Seg)</b>" + "[R]${getBundleTable()!![5]}[R]${getBundleTable()!![4]}\n" +
                                "----------------------------------\n" +
                        "[L]Temp" + "\n" + "agua <b>(C)</b>" + "[R]${getBundleTable()!![7]}[R]${getBundleTable()!![6]}\n" +
                                "----------------------------------\n" +
                        "[L]Temp" + "\n" + "ambiente <b>(C)</b>" + "[R]${getBundleTable()!![9]}[R]${getBundleTable()!![8]}\n" +
                                "----------------------------------\n" +
                        "[L]Presión de" + "\n" + "trabajo <b>(Kpa)</b>" + "[R]${getBundleTable()!![11]}[R]${getBundleTable()!![10]}\n" +
                                "----------------------------------\n" +
                        "[L]<b>Resultados" + "[R]${getBundleTable()!![13]}[R]${getBundleTable()!![12]}</b>\n"+
                                "[L]\n"
                printer.printFormattedText(text)
            } else {
                Toast.makeText(context, getString(R.string.print_not_connected), Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e("APP", "Can't print", e)
        }
    }

}