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
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentReportSavedBinding
import java.util.*

class FragmentReportSaved : Fragment() {
    private lateinit var binding: FragmentReportSavedBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentReportSavedBinding.inflate(inflater, container, false)
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
            tvKind.text = getString(R.string.kind_r) + " ${getBundleBasicInfo()!![9]}"
            tvNewOld.text = getString(R.string.estado_r) + " ${getBundleBasicInfo()!![10]}"
            // Table
            tiRH0.text = getBundleTable()!![0]
            tiQ2LI.text = getBundleTable()!![1]
            tiQ1LI.text = getBundleTable()!![2]
            tiQ2LF.text = getBundleTable()!![3]
            tiQ1LF.text = getBundleTable()!![4]
            tiQ2Time.text = getBundleTable()!![5]
            tiQ1Time.text = getBundleTable()!![6]
            tiQ2AfaroR.text = getBundleTable()!![7]
            tiQ1AforoR.text = getBundleTable()!![8]
            tiQ1TWater.text = getBundleTable()!![9]
            tiQ2TWater.text = getBundleTable()!![10]
            tiQ1TEnvironment.text = getBundleTable()!![11]
            tiQ2TEnvironment.text = getBundleTable()!![12]
            tiQ1WorkPressure.text = getBundleTable()!![13]
            tiQ2WorkPressure.text = getBundleTable()!![14]
            tvQ2Aforo.text = getBundleTable()!![15]
            tvQ1Aforo.text = getBundleTable()!![16]
            tvResult.text = getBundleTable()!![17]
            tvQ2Process.text = getBundleTable()!![18]
            tvQ1Process.text = getBundleTable()!![19]
            tiLiTableNew.text = getBundleTable()!![20]

            // Show/hide information tables
            val approvedInfo = tvResult.text.toString().replace("\n", " ")
            if (approvedInfo.contains(getString(R.string.rechazado), ignoreCase = true)) {
                deprecatedTable.visibility = View.VISIBLE
                approvedTable.visibility = View.GONE
            } else {
                deprecatedTable.visibility = View.GONE
                approvedTable.visibility = View.VISIBLE
            }

            btPrint.setOnClickListener {
                doPrint()
            }
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
                val text = "[C]<b>Prueba de medidor</b>\n" +
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
                        "[L]Tipo: ${getBundleBasicInfo()!![9]}\n" +
                        "[L]Estado: ${getBundleBasicInfo()!![10]}\n\n" +
                        approvedTable(getBundleTable()!![17])

                printer.printFormattedText(text)
            } else {
                Toast.makeText(context, getString(R.string.print_not_connected), Toast.LENGTH_SHORT)
                    .show()
            }

        } catch (e: Exception) {
            Log.e("APP", "Can't print", e)
        }
    }

    private fun approvedTable(approvedText: String): String {
        var table: String = ""

        val approvedInfo = approvedText.replace("\n", " ")
        if (approvedInfo.contains(getString(R.string.rechazado), ignoreCase = true)) {
            table = "[L]<b>Medidor rechazado</b>\n" +
                    "----------------------------------\n" +
                    "[L]Lectura" + "\n" + "inicial <b>(L)</b>" + "[R]${getBundleTable()!![0]}\n" +
                    "[L]\n"
        } else {
            table = "[L]Humedad" + "\n" + "relativa <b>(%)</b>" + "[R]${getBundleTable()!![0]}\n" +
                    "<b>----------------------------------</b>\n" +
                    "[L]    <b>MEDICIONES[R]Q2[R]Q1</b>" + "\n" +
                    "<b>----------------------------------</b>\n" +
                    "[L]Lectura" + "\n" + "inicial <b>(L)</b>" + "[R]${getBundleTable()!![1]}[R]${getBundleTable()!![2]}\n" +
                    "----------------------------------\n" +
                    "[L]Lectura" + "\n" + "final <b>(L)</b>" + "[R]${getBundleTable()!![3]}[R]${getBundleTable()!![4]}\n" +
                    "----------------------------------\n" +
                    "[L]Tiempo <b>(Seg)</b>" + "[R]${getBundleTable()!![5]}[R]${getBundleTable()!![6]}\n" +
                    "----------------------------------\n" +
                    "[L]Aforo " + "\n" + "real <b>(Seg)</b>" + "[R]${getBundleTable()!![7]}[R]${getBundleTable()!![8]}\n" +
                    "----------------------------------\n" +
                    "[L]Temp" + "\n" + "agua <b>(C)</b>" + "[R]${getBundleTable()!![9]}[R]${getBundleTable()!![10]}\n" +
                    "----------------------------------\n" +
                    "[L]Temp" + "\n" + "ambiente <b>(C)</b>" + "[R]${getBundleTable()!![11]}[R]${getBundleTable()!![12]}\n" +
                    "----------------------------------\n" +
                    "[L]Presión de" + "\n" + "trabajo <b>(Kpa)</b>" + "[R]${getBundleTable()!![13]}[R]${getBundleTable()!![14]}\n" +
                    "----------------------------------\n" +
                    "[L]<b>Aforo calculado:</b>" + "[R]${getBundleTable()!![15]}[R]${getBundleTable()!![16]}\n" +
                    "----------------------------------\n" +
                    "[L]<b>${getBundleTable()!![17]}" + "[R]${getBundleTable()!![18]}[R]${getBundleTable()!![19]}</b>\n" +
                    "[L]\n"
        }

        return table
    }

}