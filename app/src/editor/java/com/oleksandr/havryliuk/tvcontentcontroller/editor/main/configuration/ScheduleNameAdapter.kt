package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleksandr.havryliuk.tvcontentcontroller.R
import kotlinx.android.synthetic.main.schedule_item.view.*

class ScheduleNameAdapter(
        private val scheduleNames: List<String>,
        private val onDeleteClick: (itemName: String) -> Boolean
) : RecyclerView.Adapter<ScheduleNameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.schedule_item, parent, false)
    )

    override fun getItemCount() = scheduleNames.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(scheduleNames[position], position, onDeleteClick)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: String, position: Int, onDeleteClick: (item: String) -> Boolean) {
            view.tv_schedule_name.text = item.replace("+", " ")

            view.delete_button_iv.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }
}