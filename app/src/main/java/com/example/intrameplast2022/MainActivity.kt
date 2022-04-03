package com.example.intrameplast2022

import CourseModal
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intrameplast2022.dataSource.Prefs
import com.example.intrameplast2022.ui.fragment.FragmentMenu1
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {

    companion object{ // Todos podrán acceder a este método con companion object
        lateinit var prefs: Prefs
        @SuppressLint("StaticFieldLeak")

        lateinit var load: MainActivity
        var courseModalArrayList: ArrayList<CourseModal>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.addLogAdapter(AndroidLogAdapter())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefs = Prefs(applicationContext)
        loadData()
    }

    private fun loadData() {
        // method to load arraylist from shared prefs
        // initializing our shared prefs with name as
        // shared preferences.
        val sharedPreferences = getSharedPreferences("shared preferences",
            AppCompatActivity.MODE_PRIVATE
        )

        // creating a variable for gson.
        val gson = Gson()

        // below line is to get to string present from our
        // shared prefs if not present setting it as null.
        val json = sharedPreferences?.getString("courses", null)

        // below line is to get the type of our array list.
        val type = object : TypeToken<ArrayList<CourseModal?>?>() {}.type

        // in below line we are getting data from gson
        // and saving it to our array list
        courseModalArrayList = gson.fromJson<ArrayList<CourseModal>>(json, type)

        // checking below if the array list is empty or not
        if (courseModalArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            courseModalArrayList = ArrayList<CourseModal>()
        }
    }

    fun saveData() {
        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        val sharedPreferences = getSharedPreferences("shared preferences",
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
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show()
    }

}