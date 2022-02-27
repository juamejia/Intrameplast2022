package com.example.intrameplast2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.intrameplast2022.dataSource.Prefs

class MainActivity : AppCompatActivity() {

    companion object{ // Todos podrán acceder a este método con companion object
        lateinit var prefs: Prefs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = Prefs(applicationContext)
    }
}