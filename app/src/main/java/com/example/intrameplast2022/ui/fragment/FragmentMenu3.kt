package com.example.intrameplast2022.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.FragmentHomeBinding
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
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE, getString(R.string.file_name_txt))
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
            try {
                val outputStream = context?.contentResolver?.openOutputStream(uri!!)
                outputStream?.write("CodeLib File save Demo".toByteArray())
                outputStream?.close()
                Toast.makeText(context, getString(R.string.god_exportation), Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                print(e.localizedMessage)
                Toast.makeText(context, getString(R.string.bad_exportation), Toast.LENGTH_SHORT).show()
            }
        }
    }

}