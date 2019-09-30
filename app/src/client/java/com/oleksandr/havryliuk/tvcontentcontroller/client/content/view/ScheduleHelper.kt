package com.oleksandr.havryliuk.tvcontentcontroller.client.content.view

import android.view.View
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.ScheduleDayAdapter
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.Schedule
import kotlinx.android.synthetic.main.post_schedule.view.*

fun showScheduleView(schedule: Schedule, root: View) {
    root.recycler_view_1.adapter = ScheduleDayAdapter(schedule.days[0])
    root.recycler_view_2.adapter = ScheduleDayAdapter(schedule.days[1])
    root.recycler_view_3.adapter = ScheduleDayAdapter(schedule.days[2])
    root.recycler_view_4.adapter = ScheduleDayAdapter(schedule.days[3])
    root.recycler_view_5.adapter = ScheduleDayAdapter(schedule.days[4])

    root.tv_group_name.text = schedule.name.replace("+", " ")
}