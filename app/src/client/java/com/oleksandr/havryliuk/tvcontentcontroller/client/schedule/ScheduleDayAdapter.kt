package com.oleksandr.havryliuk.tvcontentcontroller.client.schedule

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oleksandr.havryliuk.tvcontentcontroller.R
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.*
import kotlinx.android.synthetic.main.shedule_lesson_item.view.*
import org.jetbrains.anko.backgroundColor

class ScheduleDayAdapter(
        private val day: ScheduleDay
) : RecyclerView.Adapter<ScheduleDayAdapter.ViewHolder>() {

    init {
        while (day.lessonsList.size < 6) {
            day.lessonsList.add(mutableListOf())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.shedule_lesson_item, parent, false)
    )

    override fun getItemCount() = day.lessonsList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(day.lessonsList[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: List<Lesson>, position: Int) {
            with(itemView) {

                tv_number_of_lesson.text = position.plus(1).toString()

                for (lesson: Lesson in item) {
                    when (lesson) {
                        is FullGroup -> {
                            full_layout.visibility = View.VISIBLE
                            sub_layout.visibility = View.GONE
                            tv_full_group_body_of_lesson.text = Html.fromHtml(lesson.htmlBody)
                            tv_full_group_body_of_lesson.backgroundColor = resources.getColor(R.color.light_green)
                        }
                        is Sub1Group -> {
                            full_layout.visibility = View.GONE
                            sub_layout.visibility = View.VISIBLE
                            tv_sub_1_group_body_of_lesson.text = Html.fromHtml(lesson.htmlBody)
                            tv_sub_1_group_body_of_lesson.backgroundColor = resources.getColor(R.color.light_green)
                        }
                        is Sub2Group -> {
                            full_layout.visibility = View.GONE
                            sub_layout.visibility = View.VISIBLE
                            tv_sub_2_group_body_of_lesson.text = Html.fromHtml(lesson.htmlBody)
                            tv_sub_2_group_body_of_lesson.backgroundColor = resources.getColor(R.color.light_green)
                        }
                    }
                }
            }
        }
    }
}