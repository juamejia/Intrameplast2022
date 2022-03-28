package com.example.intrameplast2022

import android.Manifest
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Printer {
    val PERMISSION_BLUETOOTH = 1

    private val locale = Locale("id", "ID")
    private val df: DateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", locale)
    private val nf = NumberFormat.getCurrencyInstance(locale)

    fun doPrint() {
        try {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH),
                    MainActivity.PERMISSION_BLUETOOTH
                )
            } else {
                val connection = BluetoothPrintersConnections.selectFirstPaired()
                if (connection != null) {
                    val printer = EscPosPrinter(connection, 203, 48f, 32)
                    val text = "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                        printer,
                        this.getApplicationContext().getResources().getDrawableForDensity(
                            R.drawable.ic_whale_11,
                            DisplayMetrics.DENSITY_LOW, getTheme()
                        )
                    ) + "</img>\n" +
                            "[L]\n" +
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
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("APP", "Can't print", e)
        }
    }
}