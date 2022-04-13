package com.example.intrameplast2022.ui.fragment

import CourseModal
import android.content.Intent
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.MainActivity.Companion.courseModalArrayList
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentMenu3Binding
import java.lang.Exception
import kotlin.system.exitProcess

class FragmentMenu3 : Fragment() {

    private lateinit var binding: FragmentMenu3Binding
    val CREATE_FILE = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenu3Binding.inflate(inflater, container, false)
        // Back and Exit buttons, always the same in all fragments
        binding.btExit.setOnClickListener { exitProcess(0) }
        binding.btBack.setOnClickListener {
            findNavController().popBackStack() // Return to the preview fragment, in this case, always homeFragment
        }

        // Export button code
        binding.btExport.setOnClickListener {

            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/html"
                putExtra(Intent.EXTRA_TITLE, getString(R.string.file_name_html))
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, "")
            }
            startActivityForResult(intent, CREATE_FILE)

        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_FILE && resultCode == AppCompatActivity.RESULT_OK) {
            val uri = data!!.data
            var mainHTML = ""
            try {
                courseModalArrayList!!.forEach {
                    mainHTML += reportsConstructor(it)
                }
                println(mainHTML)

                val outputStream = context?.contentResolver?.openOutputStream(uri!!)
                outputStream?.write(mainHTML.toByteArray())
                outputStream?.close()
                Toast.makeText(context, getString(R.string.god_exportation), Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                print(e.localizedMessage)
                Toast.makeText(context, getString(R.string.bad_exportation), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun reportsConstructor(position: CourseModal): String { // create one HTML file with all records
        // Basic info
        val fecha: String = position.basicInfo[0]
        val operador: String = position.basicInfo[1]
        val usuario: String = position.basicInfo[2]
        val direccion: String = position.basicInfo[3]
        val contrato: String = position.basicInfo[4]
        val marca: String = position.basicInfo[5]
        val serial: String = position.basicInfo[6]
        val calibre: String = position.basicInfo[7]
        val claseM: String = position.basicInfo[8]
        val estado: String = position.basicInfo[9]
        // Table
        val table0: String = position.tableInfo[0]
        val table1: String = position.tableInfo[1]
        val table2: String = position.tableInfo[2]
        val table3: String = position.tableInfo[3]
        val table4: String = position.tableInfo[4]
        val table5: String = position.tableInfo[5]
        val table6: String = position.tableInfo[6]
        val table7: String = position.tableInfo[7]
        val table8: String = position.tableInfo[8]
        val table9: String = position.tableInfo[9]
        val table10: String = position.tableInfo[10]
        val table11: String = position.tableInfo[11]
        val table12: String = position.tableInfo[12]
        val table13: String = position.tableInfo[13]
        val table14: String = position.tableInfo[14]
        val table15: String = position.tableInfo[15]

        return "<!DOCTYPE html> <html lang='es'> <head> <meta charset='UTF-8'> <style> div.content { max-width: 500px } </style> </head> <body> <div class='content'> <hr></hr> <h1 style='text-align: center;'><u>Prueba de medidor</u></h1> <hr></hr> <p><strong>Fecha:</strong> $fecha</p> <p><strong>Operador: $operador</strong></p> <p><strong>Usuario:</strong> $usuario</p> <p><strong>Direcci&oacute;n:</strong> $direccion</p> <p><strong>N&uacute;mero de contrato:</strong> $contrato</p> <p>&nbsp;</p> <h1 style='text-align: center;'>Especificaciones</h1> <p><strong>Marca:</strong> $marca</p> <p><strong>Serial:</strong> $serial</p> <p><strong>Calibre:</strong> $calibre</p> <p><strong>Clase metrol&oacute;gica:</strong> $claseM</p> <p><strong>Estado:</strong> $estado</p> <p>&nbsp;</p> <table style='margin-left: auto; margin-right: auto; border-spacing: 0' border='1'> <tbody> <tr> <td style='width: 178.336px; text-align: center;'><h4><span style='text-decoration: underline;'><strong>MEDICIONES</strong></span> </h4></td> <td style='width: 26.7031px; text-align: center;'><span style='text-decoration: underline;'><strong>Q1</strong></span></td> <td style='width: 26.7031px; text-align: center;'><span style='text-decoration: underline;'><strong>Q2</strong></span></td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Lectura inicial <strong>[L]</strong></td> <td style='width: 26.7031px;'>$table0</td> <td style='width: 26.7031px;'>$table1</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Lectura final <strong>[L]</strong></td> <td style='width: 26.7031px;'>$table2</td> <td style='width: 26.7031px;'>$table3</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Tiempo <strong>[Seg]</strong></td> <td style='width: 26.7031px;'>$table4</td> <td style='width: 26.7031px;'>$table5</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Aforo real <strong>[L]</strong></td> <td style='width: 26.7031px;'>$table6</td> <td style='width: 26.7031px;'>$table7</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Temp agua <strong>[C&ordm;]</strong></td> <td style='width: 26.7031px;'>$table8</td> <td style='width: 26.7031px;'>$table9</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Temp ambiente <strong>[C&ordm;]</strong></td> <td style='width: 26.7031px;'>$table10</td> <td style='width: 26.7031px;'>$table11</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'>Presi&oacute;n de trabajo <strong>[Kpa]</strong></td> <td style='width: 26.7031px;'>$table12</td> <td style='width: 26.7031px;'>$table13</td> </tr> <tr style='text-align: center;'> <td style='width: 178.336px;'><p><span style='color: #339966;'><strong>Resultados</strong></span></p></td> <td style='width: 26.7031px;'><p><strong>$table14</strong></p></td> <td style='width: 26.7031px;'><p><strong>$table15</strong></p></td> </tr> </tbody> </table> <p>&nbsp;</p></div> </body> </html>"
    }

}