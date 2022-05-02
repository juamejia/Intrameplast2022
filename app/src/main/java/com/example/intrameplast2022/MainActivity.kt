package com.example.intrameplast2022

import com.example.intrameplast2022.dataSource.CourseModal
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.intrameplast2022.dataSource.Prefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    companion object {
        lateinit var prefs: Prefs

        @SuppressLint("StaticFieldLeak")
        var courseModalArrayList: ArrayList<CourseModal>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.addLogAdapter(AndroidLogAdapter())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = Prefs(applicationContext)
        loadData()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun loadData() {
        val sharedPreferences = getSharedPreferences(
            "shared preferences",
            MODE_PRIVATE
        )
        val gson = Gson()
        val json = sharedPreferences?.getString("courses", null)
        val type = object : TypeToken<ArrayList<CourseModal?>?>() {}.type
        courseModalArrayList = gson.fromJson<ArrayList<CourseModal>>(json, type)
        if (courseModalArrayList == null) {
            courseModalArrayList = ArrayList<CourseModal>()
        }
    }

}