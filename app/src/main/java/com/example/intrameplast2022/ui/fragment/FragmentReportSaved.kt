package com.example.intrameplast2022.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.example.intrameplast2022.Printer
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
            doPrint()
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

    fun doPrint() {
        try {
            val connection = BluetoothPrintersConnections.selectFirstPaired()
            if (connection != null) {
                val printer = EscPosPrinter(connection, 203, 48f, 32)
                val text = "[L]\n" +
                        "[L]" + df.format(Date()) + "\n" +
                        "[C]================================\n" +
                        "[L]<b>Effective Java</b>\n" +
                        "[L]    1 pcs[R]" + nf.format(25000) + "\n" +
                        "[L]<b>Headfirst Android Development</b>\n" +
                        "[L]    1 pcs[R]" + nf.format(45000) + "\n" +
                        "[L]<b>The Martian</b>\n" +
                        "[L]    1 pcs[R]" + nf.format(20000) + "\n" +
                        "[C]--------------------------------\n" +
                        "[L]TOTAL[R]" + nf.format(90000) + "\n" +
                        "[L]DISCOUNT 15%[R]" + nf.format(13500) + "\n" +
                        "[L]TAX 10%[R]" + nf.format(7650) + "\n" +
                        "[L]<b>GRAND TOTAL[R]" + nf.format(84150) + "</b>\n" +
                        "[C]--------------------------------\n" +
                        "[C]<barcode type='ean13' height='10'>202105160005</barcode>\n" +
                        "[C]--------------------------------\n" +
                        "[C]Thanks For Shopping\n" +
                        "[C]https://kodejava.org\n" +
                        "[L]\n" +
                        "[L]<qrcode>https://kodejava.org</qrcode>\n"
                printer.printFormattedText(text)
            } else {
                Toast.makeText(context, getString(R.string.print_not_connected), Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e("APP", "Can't print", e)
        }
    }

}