package com.example.intrameplast2022.ui.viewmodel

import CourseModal
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intrameplast2022.MainActivity.Companion.courseModalArrayList
import com.example.intrameplast2022.R
import com.example.intrameplast2022.databinding.ItemRecordBinding

class RecordAdapter(
    private var recordList: ArrayList<CourseModal>,
    private val onClickListener: (CourseModal) -> Unit
) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_record, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recordList[position]
        holder.render(item, onClickListener) // to send data to selected item
    }

    override fun getItemCount(): Int = recordList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemRecordBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun render(recordModel: CourseModal, onClickListener: (CourseModal) -> Unit) {
            with(binding) {
                tvTitle.text = "Operador: ${recordModel.getBasicInfo()[1]}"
                tvDescription.text = "Fecha: ${recordModel.getBasicInfo()[0]}"
                itemView.setOnClickListener { onClickListener(recordModel) }
            }
        }

    }

    // creating a constructor for our variables.
    init {
        this.recordList = recordList
    }

}