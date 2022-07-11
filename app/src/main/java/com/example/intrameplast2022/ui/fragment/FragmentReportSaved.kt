package com.example.intrameplast2022.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentReportSavedBinding
import java.util.*

@Suppress("DEPRECATION")
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
            tiLM0.text = getBundleTable()!![0]
            tiRH0.text = getBundleTable()!![1]
            tiQ2LI.text = getBundleTable()!![2]
            tiQ1LI.text = getBundleTable()!![3]
            tiQ2LF.text = getBundleTable()!![4]
            tiQ1LF.text = getBundleTable()!![5]
            tiQ2Time.text = getBundleTable()!![6]
            tiQ1Time.text = getBundleTable()!![7]
            tiQ2AfaroR.text = getBundleTable()!![8]
            tiQ1AforoR.text = getBundleTable()!![9]
            tiQ1TWater.text = getBundleTable()!![10]
            tiQ2TWater.text = getBundleTable()!![11]
            tiQ1TEnvironment.text = getBundleTable()!![12]
            tiQ2TEnvironment.text = getBundleTable()!![13]
            tiQ1WorkPressure.text = getBundleTable()!![14]
            tiQ2WorkPressure.text = getBundleTable()!![15]
            tvQ2Aforo.text = getBundleTable()!![16]
            tvQ1Aforo.text = getBundleTable()!![17]
            tvResult.text = getBundleTable()!![18]
            tvQ2Process.text = getBundleTable()!![19]
            tvQ1Process.text = getBundleTable()!![20]
            tvReject2.text = getBundleTable()!![21]

            // New meter
            if (getNewMeterTable()!!.isNotEmpty()) {
                tvMarkNew.text = getString(R.string.marca_r) + " ${getNewMeterTable()!![0]}"
                tvSerialNew.text = getString(R.string.serial_r) + " ${getNewMeterTable()!![1]}"
                tvCaliberNew.text = getString(R.string.calibre_r) + " ${getNewMeterTable()!![2]}"
                tvMetrologicalClassNew.text =
                    getString(R.string.clase_metrol_gica_r) + " ${getNewMeterTable()!![3]}"
                tvKindNew.text = getString(R.string.kind_r) + " ${getNewMeterTable()!![4]}"
                tvNewOldNew.text = getString(R.string.estado_r) + " ${getNewMeterTable()!![5]}"
            }

            // Show/hide information tables
            val approvedInfo = tvResult.text.toString().replace("\n", " ")
            if (approvedInfo.contains(getString(R.string.rechazado), ignoreCase = true)) {
                tvMeterInfo.text = "Medidor ${getString(R.string.rechazado)}"
                approvedTable.visibility = View.VISIBLE
                if (tvReject2.text.toString() == "") {
                    deprecatedTable.visibility = View.GONE
                    newSpecifications.visibility = View.GONE
                } else {
                    deprecatedTable.visibility = View.VISIBLE
                    newSpecifications.visibility = View.VISIBLE
                }
            } else {
                tvMeterInfo.text = "Medidor ${getString(R.string.aprobado)}"
                deprecatedTable.visibility = View.GONE
                approvedTable.visibility = View.VISIBLE
                newSpecifications.visibility = View.VISIBLE
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
        return arguments?.get("recordSelected") as ArrayList<String>?
    }

    private fun getBundleTable(): ArrayList<String>? {
        return arguments?.get("tableSelected") as ArrayList<String>?
    }

    private fun getNewMeterTable(): ArrayList<String>? {
        return arguments?.get("newMeterSelected") as ArrayList<String>?
    }

    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bm, 0, 0, width, height, matrix, false
        )
        bm.recycle()
        return resizedBitmap
    }

    private fun doPrint() {
        try {
            val connection = BluetoothPrintersConnections.selectFirstPaired()
            if (connection != null) {
                val printer = EscPosPrinter(connection, 203, 78f, 44)
                val passedOrNot = getBundleTable()!![18].replace(
                    "\n",
                    " "
                ) // Contains information about the measure pass or not pass.
                val text =
                    "[C]<u><font size='tall'>METROLOGÍA AGUA Y SERVICIO</font></u>\n" +
                            "[C]Prueba de medidor\n" +
                            "[C]--------------------------------\n" +
                            "[L]Fecha: ${getBundleBasicInfo()!![0]}\n" +
                            "[L]Operador: ${getBundleBasicInfo()!![1]}\n" +
                            "[L]Usuario: ${getBundleBasicInfo()!![2]}\n" +
                            "[L]Dirección: ${getBundleBasicInfo()!![3]}\n" +
                            "[L]Número de contrato: ${getBundleBasicInfo()!![4]}\n" +
                            "[C]====================================\n" +
                            "[C]<b>Especificaciones Medidor instalado</b>\n" +
                            "[L]Marca: ${getBundleBasicInfo()!![5]}\n" +
                            "[L]Serial: ${getBundleBasicInfo()!![6]}\n" +
                            "[L]Calibre: ${getBundleBasicInfo()!![7]}\n" +
                            "[L]Clase metrológica: ${getBundleBasicInfo()!![8]}\n" +
                            "[L]Tipo: ${getBundleBasicInfo()!![9]}\n" +
                            "[L]Estado: ${getBundleBasicInfo()!![10]}\n\n" +
                            "[C]<b><u>Medidor ${passedOrNot}</u></b>\n" +
                            approvedTable(getBundleTable()!![21]) +
                            "[L]Lectura <b>(mts 3)</b>" + "[R]${getBundleTable()!![0]}\n" +
                            "[L]Humedad" + "\n" + "relativa <b>(%)</b>" + "[R]${getBundleTable()!![1]}\n" +
                            "<b>----------------------------------------------</b>\n" +
                            "[L]    <b>MEDICIONES[R]Q2[R]Q1</b>" + "\n" +
                            "<b>----------------------------------------------</b>\n" +
                            "[L]Lectura" + "\n" + "inicial <b>(mts 3)</b>" + "[R]${getBundleTable()!![2]}[R]${getBundleTable()!![3]}\n" +
                            "----------------------------------------------\n" +
                            "[L]Lectura" + "\n" + "final <b>(L)</b>" + "[R]${getBundleTable()!![4]}[R]${getBundleTable()!![5]}\n" +
                            "----------------------------------------------\n" +
                            "[L]Tiempo <b>(Seg)</b>" + "[R]${getBundleTable()!![6]}[R]${getBundleTable()!![7]}\n" +
                            "----------------------------------------------\n" +
                            "[L]Aforo " + "\n" + "real <b>(Seg)</b>" + "[R]${getBundleTable()!![8]}[R]${getBundleTable()!![9]}\n" +
                            "----------------------------------------------\n" +
                            "[L]Temp" + "\n" + "agua <b>(C)</b>" + "[R]${getBundleTable()!![10]}[R]${getBundleTable()!![11]}\n" +
                            "----------------------------------------------\n" +
                            "[L]Temp" + "\n" + "ambiente <b>(C)</b>" + "[R]${getBundleTable()!![12]}[R]${getBundleTable()!![13]}\n" +
                            "----------------------------------------------\n" +
                            "[L]Presión de" + "\n" + "trabajo <b>(Kpa)</b>" + "[R]${getBundleTable()!![14]}[R]${getBundleTable()!![15]}\n" +
                            "----------------------------------------------\n" +
                            "[L]<b>Aforo calculado:</b>" + "[R]${getBundleTable()!![16]}[R]${getBundleTable()!![17]}\n" +
                            "----------------------------------------------\n" +
                            "[L]<b>${getBundleTable()!![18]}" + "[R]${getBundleTable()!![19]}[R]${getBundleTable()!![20]}</b>\n" +
                            approvedTable2(passedOrNot, printer) +
                            "[L]\n" + "\n\n_______________________________\n" +
                            "[C]Firma del cliente" +
                            "[L]\n" +
                            "[L]\n"

                printer.printFormattedText(text)
            } else {
                Toast.makeText(context, getString(R.string.print_not_connected), Toast.LENGTH_SHORT)
                    .show()
            }

        } catch (e: Exception) {
            Log.e("APP", "Can't print", e)
        }
    }

    private fun approvedTable(finalReadingText: String): String {
        var table: String = ""
        table = if (finalReadingText != "") {
            "----------------------------------------------\n" +
                    "[L]Lectura" + "\n" + "inicial <b>(mts 3)</b>" + "[R]${getBundleTable()!![21]}\n" +
                    "[L]\n"
        } else {
            ""
        }
        return table
    }

    private fun approvedTable2(passed: String, printer: EscPosPrinter): String {
        var table2: String = ""
        if (getNewMeterTable()!!.isEmpty()) return table2
        table2 = if (passed.contains(
                "Rechazado",
                ignoreCase = true
            ) && binding.newSpecifications.visibility == View.VISIBLE
        ) {
            "\n[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                printer,
                getResizedBitmap(getBundlePhoto(), 400, 400)
            ) + "</img>\n" +
                    "[C]================================\n" +
                    "[C]<b>Especificaciones Medidor Nuevo</b>\n" +
                    "[L]Marca: ${getNewMeterTable()!![0]}\n" +
                    "[L]Serial: ${getNewMeterTable()!![1]}\n" +
                    "[L]Calibre: ${getNewMeterTable()!![2]}\n" +
                    "[L]Clase metrológica: ${getNewMeterTable()!![3]}\n" +
                    "[L]Tipo: ${getNewMeterTable()!![4]}\n" +
                    "[L]Estado: ${getNewMeterTable()!![5]}\n"
        } else {
            ""
        }

        return table2
    }

}